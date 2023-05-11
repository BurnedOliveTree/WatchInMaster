package pw.edu.watchin.server.dto.stream;

import lombok.Value;
import pw.edu.watchin.server.domain.video.VideoVisibilityType;
import pw.edu.watchin.server.dto.channel.ChannelTileDTO;
import pw.edu.watchin.server.dto.resource.ResourceDTO;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class EditableStreamDTO {
    UUID id;
    String title;
    String description;
    String uploadUrl;
    String watchUrl;
    ChannelTileDTO author;
    ResourceDTO thumbnail;
    LocalDateTime uploaded;
    VideoVisibilityType visibility;
}
