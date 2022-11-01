package pw.edu.watchin.server.domain.video;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@Getter
@Setter
@RedisHash("video:transcoding")
public class VideoTranscodingHash {

    @Id
    private UUID videoId;

    private String sourceLocation;

    private VideoQualityType requestedQuality;
}
