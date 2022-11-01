package pw.edu.watchin.server.domain.account;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.Date;

@Getter
@Setter
@RedisHash("account:remember-me-token")
public class RememberMeTokenHash {

    @Id
    private String series;

    private String token;

    private Date lastUsed;

    @Indexed
    private String username;
}
