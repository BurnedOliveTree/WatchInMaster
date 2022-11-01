package pw.edu.watchin.server.dto.channel;

import lombok.Value;
import pw.edu.watchin.server.dto.resource.ResourceDTO;

@Value
public class ChannelEditDTO {
    String description;
    ResourceDTO avatar;
    ResourceDTO background;
}
