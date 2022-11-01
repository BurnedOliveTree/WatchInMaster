package pw.edu.watchin.server.service.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.edu.watchin.server.domain.video.VideoCommentEntity;
import pw.edu.watchin.server.domain.video.VideoEntity;
import pw.edu.watchin.server.dto.video.*;
import pw.edu.watchin.server.service.channel.ChannelMapperService;
import pw.edu.watchin.server.service.resource.ResourceMapperService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoMapperService {

    @Autowired
    private ResourceMapperService resourceMapperService;

    @Autowired
    private ChannelMapperService channelMapperService;

    public VideoDTO mapDetails(VideoEntity video, VideoLikesDTO likes) {
        return new VideoDTO(
            video.getId(),
            video.getTitle(),
            video.getDescription(),
            video.getLength(),
            video.getUploaded(),
            video.getViews(),
            video.getVisibility(),
            channelMapperService.mapTile(video.getChannel()),
            mapVideoResources(video),
            likes,
            new VideoFavoriteDTO(!video.getFavorites().isEmpty()),
            new VideoWatchLaterDTO(!video.getWatchLaterList().isEmpty())
        );
    }

    private List<VideoResourceDTO> mapVideoResources(VideoEntity video) {
        return video.getResources()
            .stream()
            .map(entity -> new VideoResourceDTO(
                resourceMapperService.getResourceLocation(entity.getResource()),
                new VideoQualityDTO(entity.getQuality(), entity.getQuality().getFriendlyName())
            ))
            .collect(Collectors.toUnmodifiableList());
    }

    public VideoTileDTO mapTile(VideoEntity video) {
        return new VideoTileDTO(
            video.getId(),
            video.getTitle(),
            video.getDescription(),
            video.getLength(),
            video.getUploaded(),
            video.getViews(),
            resourceMapperService.getResourceLocation(video.getThumbnail()),
            channelMapperService.mapTile(video.getChannel()),
            new VideoFavoriteDTO(!video.getFavorites().isEmpty()),
            new VideoWatchLaterDTO(!video.getWatchLaterList().isEmpty())
        );
    }

    public VideoEditDTO mapEdit(VideoEntity video) {
        return new VideoEditDTO(
            video.getId(),
            video.getTitle(),
            video.getDescription(),
            resourceMapperService.getResourceLocation(video.getThumbnail()),
            video.getVisibility(),
            video.getStatus()
        );
    }

    public VideoTableEntryDTO mapTableEntry(VideoEntity video) {
        return new VideoTableEntryDTO(
            video.getId(),
            video.getTitle(),
            video.getDescription(),
            video.getLength(),
            video.getUploaded(),
            video.getVisibility(),
            video.getStatus(),
            video.getViews(),
            resourceMapperService.getResourceLocation(video.getThumbnail())
        );
    }

    public VideoCommentDTO mapComment(VideoCommentEntity comment) {
        return new VideoCommentDTO(
            comment.getId(),
            comment.getContent(),
            comment.getCreationDate(),
            channelMapperService.mapTile(comment.getChannel())
        );
    }
}
