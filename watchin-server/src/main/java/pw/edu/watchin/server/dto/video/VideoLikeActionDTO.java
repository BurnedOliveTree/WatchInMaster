package pw.edu.watchin.server.dto.video;

import lombok.Value;

@Value
public class VideoLikeActionDTO {
    boolean like;
    boolean removal;
}
