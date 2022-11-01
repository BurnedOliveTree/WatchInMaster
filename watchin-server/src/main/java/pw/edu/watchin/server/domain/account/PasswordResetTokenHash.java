package pw.edu.watchin.server.domain.account;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.UUID;

@Getter
@Setter
@RedisHash("account:password-reset-token")
public class PasswordResetTokenHash {

    @Id
    private UUID token;

    private Integer accountId;

    @TimeToLive
    private long validity;
}
