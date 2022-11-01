package pw.edu.watchin.server.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Data
@ConfigurationProperties("account")
public class AccountProperties {
    private PasswordProperties password;
    private ActivationProperties activation;
    private PasswordResetProperties passwordReset;
    private RememberMeProperties rememberMe;
    private ApiProperties api;

    @Data
    public static class PasswordProperties {
        private boolean hashingEnabled;
    }

    @Data
    public static class ActivationProperties {
        private boolean required;
        private Duration tokenValidity;
    }

    @Data
    public static class PasswordResetProperties {
        private Duration tokenValidity;
    }

    @Data
    public static class RememberMeProperties {
        private String key;
        private Duration tokenValidity;
    }

    @Data
    public static class ApiProperties {
        private String loginPath;
        private String logoutPath;
        private String detailsPath;
    }
}
