package pw.edu.watchin.server.dto.video;

import lombok.Value;
import pw.edu.watchin.server.dto.channel.ChannelTileDTO;

import java.time.LocalDateTime;

@Value
public class VideoCommentDTO {
    Integer id;
    String content;
    LocalDateTime creationDate;
    ChannelTileDTO channel;
}
