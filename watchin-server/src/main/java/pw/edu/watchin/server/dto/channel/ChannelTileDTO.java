package pw.edu.watchin.server.dto.channel;

import lombok.Value;
import pw.edu.watchin.server.dto.resource.ResourceDTO;

@Value
public class ChannelTileDTO {
    String name;
    ResourceDTO avatar;
    int subscriptionsCount;
}
