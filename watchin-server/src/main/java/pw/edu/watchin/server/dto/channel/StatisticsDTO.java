package pw.edu.watchin.server.dto.channel;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class StatisticsDTO {
    LocalDateTime date;
    long subscribers;
    long videos;
    long views;
    long comments;
}
