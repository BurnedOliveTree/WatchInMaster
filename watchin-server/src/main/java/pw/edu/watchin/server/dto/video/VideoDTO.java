package pw.edu.watchin.server.dto.video;

import lombok.Value;
import pw.edu.watchin.server.domain.video.VideoVisibilityType;
import pw.edu.watchin.server.dto.channel.ChannelTileDTO;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Value
public class VideoDTO {
    UUID id;
    String title;
    String description;
    Duration length;
    LocalDateTime uploaded;
    long views;
    VideoVisibilityType visibility;
    ChannelTileDTO channel;
    List<VideoResourceDTO> resources;
    VideoLikesDTO likes;
    VideoFavoriteDTO favorite;
    VideoWatchLaterDTO watchLater;
}
