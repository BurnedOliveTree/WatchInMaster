package pw.edu.watchin.server.dto.stream;

import lombok.Value;
import pw.edu.watchin.server.domain.video.VideoVisibilityType;
import pw.edu.watchin.server.dto.channel.ChannelTileDTO;
import pw.edu.watchin.server.dto.video.VideoFavoriteDTO;
import pw.edu.watchin.server.dto.video.VideoLikesDTO;
import pw.edu.watchin.server.dto.video.VideoResourceDTO;
import pw.edu.watchin.server.dto.video.VideoWatchLaterDTO;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Value
public class WatchableStreamDTO {
    UUID id;
    String title;
    String description;
    LocalDateTime uploaded;
    Duration length;
    long views;
    ChannelTileDTO channel;
    List<VideoResourceDTO> resources;
    VideoVisibilityType visibility;
    VideoLikesDTO likes;
    VideoFavoriteDTO favorite;
    VideoWatchLaterDTO watchLater;
}
