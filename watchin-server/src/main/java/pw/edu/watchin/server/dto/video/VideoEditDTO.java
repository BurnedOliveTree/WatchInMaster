package pw.edu.watchin.server.dto.video;

import lombok.Value;
import pw.edu.watchin.server.domain.video.VideoStatusType;
import pw.edu.watchin.server.domain.video.VideoVisibilityType;
import pw.edu.watchin.server.dto.resource.ResourceDTO;

import java.util.UUID;

@Value
public class VideoEditDTO {
    UUID id;
    String title;
    String description;
    ResourceDTO thumbnail;
    VideoVisibilityType visibility;
    VideoStatusType status;
}
