package pw.edu.watchin.server.dto.video;

import lombok.Value;
import pw.edu.watchin.server.domain.video.VideoQualityType;

@Value
public class VideoQualityDTO {
    VideoQualityType type;
    String friendlyName;
}
