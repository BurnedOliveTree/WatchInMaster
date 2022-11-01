package pw.edu.watchin.server.dto.resource;

import lombok.Value;
import pw.edu.watchin.server.domain.resource.ResourceType;

@Value
public class ResourceDTO {
    String location;
    ResourceType type;
}
