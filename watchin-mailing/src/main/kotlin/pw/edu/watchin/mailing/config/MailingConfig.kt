package pw.edu.watchin.mailing.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import pw.edu.watchin.mailing.properties.MailingProperties

@Configuration
class MailingConfig {

    @Bean
    fun mailSender(mailingProperties: MailingProperties): JavaMailSender {
        return JavaMailSenderImpl().apply {
            host = mailingProperties.host
            port = mailingProperties.port
            username = mailingProperties.username
            password = mailingProperties.password
            protocol = mailingProperties.protocol
            defaultEncoding = mailingProperties.encoding
            javaMailProperties = mailingProperties.properties.toProperties()
        }
    }
}
