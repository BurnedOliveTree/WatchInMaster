package pw.edu.watchin.server.dto.video;

import lombok.Value;
import pw.edu.watchin.server.domain.video.VideoQualityType;
import pw.edu.watchin.server.dto.resource.ResourceDTO;

@Value
public class VideoResourceDTO {
    ResourceDTO resource;
    VideoQualityDTO quality;
}
