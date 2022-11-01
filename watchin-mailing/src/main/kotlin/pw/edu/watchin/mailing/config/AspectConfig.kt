package pw.edu.watchin.mailing.config

import org.springframework.aop.framework.ProxyFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pw.edu.watchin.mailing.service.MailingService
import pw.edu.watchin.mailing.aspect.MailingServiceHandler

@Configuration
class AspectConfig {

    @Bean
    fun mailService(mailingServiceHandler: MailingServiceHandler): MailingService {
        return ProxyFactory(MailingService::class.java, mailingServiceHandler).proxy as MailingService
    }
}