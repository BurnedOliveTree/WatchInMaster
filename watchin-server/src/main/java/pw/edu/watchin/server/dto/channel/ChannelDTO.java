package pw.edu.watchin.server.dto.channel;

import lombok.Value;
import pw.edu.watchin.server.dto.resource.ResourceDTO;

import java.time.LocalDateTime;

@Value
public class ChannelDTO {
    String name;
    LocalDateTime creationDate;
    String description;
    ResourceDTO avatar;
    ResourceDTO background;
    int videosCount;
    long viewsCount;
    int subscriptionsCount;
}
