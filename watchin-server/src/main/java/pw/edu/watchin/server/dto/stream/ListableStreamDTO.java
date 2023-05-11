package pw.edu.watchin.server.dto.stream;

import lombok.Value;
import pw.edu.watchin.server.dto.channel.ChannelTileDTO;
import pw.edu.watchin.server.dto.resource.ResourceDTO;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class ListableStreamDTO {
    UUID id;
    String title;
    String description;
    ChannelTileDTO channel;
    Duration length;
    LocalDateTime uploaded;
    long views;
    ResourceDTO thumbnail;
}
