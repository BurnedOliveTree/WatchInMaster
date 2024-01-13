package pw.edu.watchin.server.service.video;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pw.edu.watchin.media.dto.MediaResource;
import pw.edu.watchin.media.service.ImageFileService;
import pw.edu.watchin.server.domain.resource.ResourceEntity;
import pw.edu.watchin.server.domain.video.StreamEntity;
import pw.edu.watchin.server.domain.video.VideoVisibilityType;
import pw.edu.watchin.server.dto.pagination.PageRequest;
import pw.edu.watchin.server.dto.pagination.PageResponse;
import pw.edu.watchin.server.dto.search.VideoTableFilterDTO;
import pw.edu.watchin.server.dto.stream.EditableStreamDTO;
import pw.edu.watchin.server.dto.stream.FullStreamDTO;
import pw.edu.watchin.server.dto.stream.ListableStreamDTO;
import pw.edu.watchin.server.dto.stream.WatchableStreamDTO;
import pw.edu.watchin.server.exception.EntityNotFoundException;
import pw.edu.watchin.server.repository.channel.ChannelRepository;
import pw.edu.watchin.server.repository.video.StreamRepository;
import pw.edu.watchin.server.security.Account;
import pw.edu.watchin.server.service.resource.ResourceService;
import pw.edu.watchin.server.service.security.SecurityService;

import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class StreamService {
    @Autowired
    private StreamRepository streamRepository;
    @Autowired
    private StreamMapperService streamMapperService;
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private ImageFileService imageFileService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private SecurityService securityService;

    @Transactional
    public EditableStreamDTO generateStream(String title, Account account) {
        var stream = new StreamEntity();
        stream.setTitle(title);
        stream.setDescription(null);
        stream.setUploadUrl(rtmp);
        stream.setWatchUrl(createDASH(stream.getId()));
        stream.setChannel(channelRepository.findByAccountId(account.getId())
            .orElseThrow(EntityNotFoundException::new));
        stream.setUploaded(LocalDateTime.now());
        stream.setLength(Duration.ZERO);
        stream.setViews(0);
        stream.setVisibility(VideoVisibilityType.PRIVATE);
        stream.setThumbnail(defaultThumbnail());
        streamRepository.saveAndFlush(stream);
        stream.setWatchUrl(createDASH(stream.getId()));
        streamRepository.saveAndFlush(stream);
        return streamMapperService.toEditable(stream);
    }

    @Transactional(readOnly = true)
    public WatchableStreamDTO getStreamDetails(UUID id, Account account) {
        return streamMapperService.toWatchableStream(getStream(id, null));
    }

    @Transactional(readOnly = true)
    public PageResponse<ListableStreamDTO> findStreams(PageRequest<Void> pageRequest, Account account) {
        var streams = getStreams(pageRequest).map(streamMapperService::toListable);
        return new PageResponse<>(streams.getContent(), pageRequest.getPage(), streams.getTotalPages(), streams.getTotalElements());
    }

    @Transactional(readOnly = true)
    public PageResponse<FullStreamDTO> getAllStreams(PageRequest<VideoTableFilterDTO> pageRequest, Account account) {
        var streams = getStreams(pageRequest).map(streamMapperService::toFull);
        return new PageResponse<>(streams.getContent(), pageRequest.getPage(), streams.getTotalPages(), streams.getTotalElements());
    }

    @Transactional
    public void viewStream(UUID id, Account account) {
        streamRepository.updateViewCounter(id);
    }

    @Transactional
    public void setTitle(UUID id, String title, Account account) {
        var stream = getStream(id, account);
        stream.setTitle(title);
        streamRepository.save(stream);
    }

    @Transactional
    public void setDescription(UUID id, @Nullable String description, Account account) {
        var stream = getStream(id, account);
        stream.setDescription(description);
        streamRepository.save(stream);
    }

    @Transactional
    public void setVisibility(UUID id, VideoVisibilityType visibility, Account account) {
        var stream = getStream(id, account);
        stream.setVisibility(visibility);
        streamRepository.save(stream);
    }

    @Transactional
    public void setThumbnail(UUID id, @Nullable InputStream thumbnailSource, Account account) {
        var stream = getStream(id, account);
        var thumbnail = Optional.ofNullable(thumbnailSource)
                .map(imageFileService::handleUploadedThumbnail)
                .map(resourceService::saveThumbnail)
                .orElse(defaultThumbnail());
        stream.setThumbnail(thumbnail);
        streamRepository.save(stream);
    }

    @Transactional
    public void deleteStream(UUID id, Account account) {
        var stream = getStream(id, account);
        streamRepository.delete(stream);
    }

    StreamEntity getStream(UUID id, Account account) {
        return streamRepository.findById(id)
            .filter(stream -> account == null || securityService.ensureOwnership(stream, account))
            .orElseThrow(EntityNotFoundException::new);
    }

    Page<StreamEntity> getStreams(PageRequest pageRequest) {
        return streamRepository.findAll(pageRequest.toPageable());
    }

    @SneakyThrows
    private ResourceEntity defaultThumbnail() {
        var resource = new ClassPathResource(defaultThumbnail);
        return resourceService.saveThumbnail(
            new MediaResource(
                resource.getInputStream(),
                resource.contentLength(),
                "image/png"));
    }

    private String createHLS(UUID id) {
        return hls + "/" + id + ".m3u8";
    }

    private String createDASH(UUID id) {
        return dash + "/" + id + ".mpd";
    }

    private static final String defaultThumbnail = "thumbnail.png";
    private static final String rtmp = "rtmp://192.168.0.156/live";
    private static final String hls = "http://192.168.0.156:8082/hls";
    private static final String dash = "http://192.168.0.156:8082/dash";
}
