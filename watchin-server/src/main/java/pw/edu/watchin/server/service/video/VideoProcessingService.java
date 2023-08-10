package pw.edu.watchin.server.service.video;

import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pw.edu.watchin.common.properties.ApplicationProperties;
import pw.edu.watchin.common.properties.FileSystemProperties;
import pw.edu.watchin.mailing.service.MailingService;
import pw.edu.watchin.media.service.VideoFileService;
import pw.edu.watchin.queue.annotation.VideoTranscodingQueueHandler;
import pw.edu.watchin.queue.template.QueueTemplate;
import pw.edu.watchin.server.domain.channel.ChannelEntity;
import pw.edu.watchin.server.domain.video.*;
import pw.edu.watchin.server.exception.EntityNotFoundException;
import pw.edu.watchin.server.repository.video.StreamRepository;
import pw.edu.watchin.server.repository.video.VideoRepository;
import pw.edu.watchin.server.repository.video.VideoResourceRepository;
import pw.edu.watchin.server.repository.video.VideoTranscodingRepository;
import pw.edu.watchin.server.service.resource.ResourceService;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class VideoProcessingService {

    @Autowired
    private VideoResourceRepository videoResourceRepository;

    @Autowired
    private VideoFileService videoFileService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoTranscodingRepository videoTranscodingRepository;

    @Autowired
    private QueueTemplate queueTemplate;

    @Autowired
    private FileSystemProperties fileSystemProperties;

    @Autowired
    private MailingService mailingService;

    @Autowired
    private ApplicationProperties applicationProperties;
    @Autowired
    private StreamRepository streamRepository;

    public VideoEntity createVideo(ChannelEntity channel, MultipartFile file) throws IOException {
        var sourceFile = File.createTempFile(fileSystemProperties.getVideoSourceFilePrefix(), null);
        file.transferTo(sourceFile);

        var video = new VideoEntity();
        video.setTitle(FilenameUtils.removeExtension(file.getOriginalFilename()));
        video.setLength(videoFileService.getDuration(sourceFile));
        video.setThumbnail(resourceService.saveThumbnail(videoFileService.generateThumbnail(sourceFile)));
        video.setFrame(video.getThumbnail());
        video.setVisibility(VideoVisibilityType.PRIVATE);
        video.setStatus(VideoStatusType.PROCESSING);
        video.setChannel(channel);
        videoRepository.saveAndFlush(video);

        var videoResolution = videoFileService.getResolution(sourceFile);
        var defaultQuality = VideoQualityType.base(videoResolution.getFirst(), videoResolution.getSecond());

        var videoTranscodingProcess = new VideoTranscodingHash();
        videoTranscodingProcess.setVideoId(video.getId());
        videoTranscodingProcess.setSourceLocation(sourceFile.getAbsolutePath());
        enqueueTranscodingProcess(videoTranscodingProcess, defaultQuality);

        return video;
    }

    public VideoEntity fromStream(StreamEntity stream, File file) throws IOException {
        var video = new VideoEntity();
        video.setTitle(stream.getTitle());
        video.setLength(videoFileService.getDuration(file));
        video.setThumbnail(resourceService.saveThumbnail(videoFileService.generateThumbnail(file)));
        video.setFrame(video.getThumbnail());
        video.setVisibility(stream.getVisibility());
        video.setStatus(VideoStatusType.PROCESSING);
        video.setChannel(stream.getChannel());
        video.setViews(stream.getViews());
        video.setUploaded(stream.getUploaded());
        // TODO transfer likes, favorites, watchLater etc
        videoRepository.saveAndFlush(video);
        streamRepository.delete(stream);

        var videoResolution = videoFileService.getResolution(file);
        var defaultQuality = VideoQualityType.base(videoResolution.getFirst(), videoResolution.getSecond());

        var videoTranscodingProcess = new VideoTranscodingHash();
        videoTranscodingProcess.setVideoId(video.getId());
        videoTranscodingProcess.setSourceLocation(file.getAbsolutePath());
        enqueueTranscodingProcess(videoTranscodingProcess, defaultQuality);

        return video;
    }

    @VideoTranscodingQueueHandler
    @Transactional
    @SuppressWarnings({"unused", "ResultOfMethodCallIgnored"})
    public void transcodingHandler(UUID transcodingProcessId) throws IOException {
        var videoTranscodingProcess = videoTranscodingRepository.findById(transcodingProcessId)
            .orElseThrow(EntityNotFoundException::new);

        var requestedQuality = videoTranscodingProcess.getRequestedQuality();

        var video = videoRepository.findById(videoTranscodingProcess.getVideoId()).orElseThrow(() -> {
            abortTranscodingProcess(videoTranscodingProcess);
            return new EntityNotFoundException();
        });

        var sourceFile = new File(videoTranscodingProcess.getSourceLocation());

        var targetFile = File.createTempFile(fileSystemProperties.getVideoTranscodingFilePrefix(), null);
        targetFile.deleteOnExit();

        var transcodedVideo = videoFileService.transcode(
            sourceFile,
            targetFile,
            requestedQuality.getWidth(),
            requestedQuality.getHeight()
        );

        var resource = resourceService.saveVideo(transcodedVideo);

        targetFile.delete();

        var videoResource = new VideoResourceEntity();
        videoResource.setVideo(video);
        videoResource.setResource(resource);
        videoResource.setQuality(requestedQuality);
        videoResource.setOrdinal(requestedQuality.ordinal());
        videoResourceRepository.save(videoResource);

        video.setStatus(VideoStatusType.PARTIALLY_READY);

        requestedQuality.next().ifPresentOrElse(
            nextRequestedQuality -> enqueueTranscodingProcess(videoTranscodingProcess, nextRequestedQuality),
            () -> finishTranscodingProcess(videoTranscodingProcess, video)
        );
    }

    private void enqueueTranscodingProcess(VideoTranscodingHash videoTranscodingProcess, VideoQualityType requestedQuality) {
        videoTranscodingProcess.setRequestedQuality(requestedQuality);
        videoTranscodingRepository.save(videoTranscodingProcess);
        queueTemplate.enqueueVideoTranscoding(videoTranscodingProcess.getVideoId());
    }

    @SneakyThrows
    private void abortTranscodingProcess(VideoTranscodingHash videoTranscodingProcess) {
        Files.deleteIfExists(Path.of(videoTranscodingProcess.getSourceLocation()));
        videoTranscodingRepository.delete(videoTranscodingProcess);
    }

    @SneakyThrows
    private void finishTranscodingProcess(VideoTranscodingHash videoTranscodingProcess, VideoEntity video) {
        abortTranscodingProcess(videoTranscodingProcess);
        video.setStatus(VideoStatusType.READY);
        mailingService.sendVideoReadyEmail(
            video.getChannel().getAccount().getEmail(),
            video.getChannel().getAccount().getUsername(),
            video.getTitle(),
            buildDirectVideoLink(video)
        );
    }

    @SneakyThrows(MalformedURLException.class)
    private String buildDirectVideoLink(VideoEntity video) {
        var videoUrl = applicationProperties.getFrontend().getUrl()
            + applicationProperties.getDeepLinking().getVideo()
            + video.getId();
        return new URL(videoUrl).toString();
    }
}
