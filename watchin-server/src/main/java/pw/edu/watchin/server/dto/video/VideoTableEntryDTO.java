package pw.edu.watchin.server.dto.video;

import lombok.Value;
import pw.edu.watchin.server.domain.video.VideoStatusType;
import pw.edu.watchin.server.domain.video.VideoVisibilityType;
import pw.edu.watchin.server.dto.channel.ChannelTileDTO;
import pw.edu.watchin.server.dto.resource.ResourceDTO;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class VideoTableEntryDTO {
    UUID id;
    String title;
    String description;
    Duration length;
    LocalDateTime uploaded;
    VideoVisibilityType visibility;
    VideoStatusType status;
    long views;
    ChannelTileDTO author;
    ResourceDTO thumbnail;
}
