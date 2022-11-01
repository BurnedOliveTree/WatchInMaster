package pw.edu.watchin.server.dto.video;

import lombok.Value;
import pw.edu.watchin.server.dto.channel.ChannelTileDTO;
import pw.edu.watchin.server.dto.resource.ResourceDTO;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class VideoTileDTO {
    UUID id;
    String title;
    String description;
    Duration length;
    LocalDateTime uploaded;
    long views;
    ResourceDTO thumbnail;
    ChannelTileDTO channel;
    VideoFavoriteDTO favorite;
    VideoWatchLaterDTO watchLater;
}
