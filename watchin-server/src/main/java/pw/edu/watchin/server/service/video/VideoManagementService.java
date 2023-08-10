package pw.edu.watchin.server.service.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pw.edu.watchin.media.service.ImageFileService;
import pw.edu.watchin.server.domain.video.VideoEntity;
import pw.edu.watchin.server.domain.video.VideoStatusType;
import pw.edu.watchin.server.domain.video.VideoVisibilityType;
import pw.edu.watchin.server.dto.pagination.PageRequest;
import pw.edu.watchin.server.dto.pagination.PageResponse;
import pw.edu.watchin.server.dto.resource.ResourceDTO;
import pw.edu.watchin.server.dto.search.VideoTableFilterDTO;
import pw.edu.watchin.server.dto.video.VideoEditDTO;
import pw.edu.watchin.server.dto.video.VideoTableEntryDTO;
import pw.edu.watchin.server.exception.EntityNotFoundException;
import pw.edu.watchin.server.repository.channel.ChannelRepository;
import pw.edu.watchin.server.repository.video.VideoRepository;
import pw.edu.watchin.server.security.Account;
import pw.edu.watchin.server.service.resource.ResourceMapperService;
import pw.edu.watchin.server.service.resource.ResourceService;
import pw.edu.watchin.server.service.security.SecurityService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@Service
public class VideoManagementService {

    @Autowired
    private VideoMapperService videoMapperService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private ImageFileService imageFileService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ResourceMapperService resourceMapperService;

    @Autowired
    private VideoProcessingService videoProcessingService;

    @Autowired
    private StreamService streamService;

    @Transactional
    public VideoEditDTO createVideo(Account account, MultipartFile file) throws IOException {
        var channel = channelRepository.findByAccountId(account.getId())
            .orElseThrow(EntityNotFoundException::new);
        var video = videoProcessingService.createVideo(channel, file);
        return videoMapperService.mapEdit(video);
    }

    @Transactional
    public VideoEditDTO fromStream(UUID streamId, File file) throws IOException {
        var stream = streamService.getStream(streamId, null);
        var video = videoProcessingService.fromStream(stream, file);
        return videoMapperService.mapEdit(video);
    }

    @Transactional(readOnly = true)
    public VideoEditDTO getVideoForEdition(UUID id, Account account) {
        return videoRepository.findById(id)
            .filter(video -> securityService.ensureOwnership(video, account))
            .map(videoMapperService::mapEdit)
            .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void deleteVideo(UUID id, Account account) {
        videoRepository.findById(id)
            .filter(video -> securityService.ensureOwnership(video, account))
            .ifPresentOrElse(videoRepository::delete, () -> {
                throw new EntityNotFoundException();
            });
    }

    @Transactional
    public String saveTitle(UUID id, String title, Account account) {
        var video = videoRepository.findById(id)
            .filter(object -> securityService.ensureOwnership(object, account))
            .orElseThrow(EntityNotFoundException::new);

        video.setTitle(title);
        return title;
    }

    @Transactional
    public String saveDescription(UUID id, @Nullable String description, Account account) {
        var video = videoRepository.findById(id)
            .filter(object -> securityService.ensureOwnership(object, account))
            .orElseThrow(EntityNotFoundException::new);

        video.setDescription(description);
        return description;
    }

    @Transactional
    public VideoVisibilityType saveVisibility(UUID id, VideoVisibilityType visibility, Account account) {
        var video = videoRepository.findById(id)
            .filter(object -> securityService.ensureOwnership(object, account))
            .orElseThrow(EntityNotFoundException::new);

        video.setVisibility(visibility);
        return visibility;
    }

    @Transactional
    public ResourceDTO saveThumbnail(UUID id, @Nullable InputStream thumbnailSource, Account account) {
        var video = videoRepository.findById(id)
            .filter(object -> securityService.ensureOwnership(object, account))
            .orElseThrow(EntityNotFoundException::new);

        var thumbnail = Optional.ofNullable(thumbnailSource)
            .map(imageFileService::handleUploadedThumbnail)
            .map(resourceService::saveThumbnail)
            .orElseGet(video::getFrame);

        video.setThumbnail(thumbnail);
        return resourceMapperService.getResourceLocation(thumbnail);
    }

    @Transactional(readOnly = true)
    public PageResponse<VideoTableEntryDTO> getAllVideos(PageRequest<VideoTableFilterDTO> pageRequest, Account account) {
        var sort = Optional.ofNullable(pageRequest.getFilter().getSortDirection())
            .flatMap(Sort.Direction::fromOptionalString)
            .flatMap(sortDirection -> Optional.ofNullable(pageRequest.getFilter().getSortField()).map(sortField -> Sort.by(sortDirection, sortField)))
            .orElse(Sort.unsorted());
        var page = videoRepository.findByChannelAccountIdAndTitleContainsIgnoreCase(
            account.getId(),
            Optional.ofNullable(pageRequest.getFilter().getPhrase()).orElse(""),
            pageRequest.toPageable(sort)
        ).map(videoMapperService::mapTableEntry);
        return new PageResponse<>(page.getContent(), pageRequest.getPage(), page.getTotalPages(), page.getTotalElements());
    }

    public VideoStatusType getVideoStatus(UUID id, Account account) {
        return videoRepository.findById(id)
            .filter(object -> securityService.ensureOwnership(object, account))
            .map(VideoEntity::getStatus)
            .orElseThrow(EntityNotFoundException::new);
    }
}
