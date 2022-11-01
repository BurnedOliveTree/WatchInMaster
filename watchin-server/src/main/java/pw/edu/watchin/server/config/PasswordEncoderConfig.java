package pw.edu.watchin.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pw.edu.watchin.server.properties.AccountProperties;

@Configuration
public class PasswordEncoderConfig {

    @SuppressWarnings("deprecation")
    @Bean
    public PasswordEncoder passwordEncoder(AccountProperties accountProperties) {
        return accountProperties.getPassword().isHashingEnabled()
            ? new BCryptPasswordEncoder()
            : NoOpPasswordEncoder.getInstance();
    }
}
