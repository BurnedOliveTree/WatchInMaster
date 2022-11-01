package pw.edu.watchin.server.dto.video;

import lombok.Value;

@Value
public class VideoLikesDTO {
    long likes;
    long dislikes;
    boolean liked;
    boolean disliked;
}
