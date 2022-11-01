package pw.edu.watchin.server.dto.channel;

import lombok.Value;

@Value
public class SubscriptionDTO {
    boolean subscribed;
    boolean available;
}
