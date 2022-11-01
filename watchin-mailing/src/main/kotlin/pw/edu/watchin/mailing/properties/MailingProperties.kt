package pw.edu.watchin.mailing.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("mailing")
data class MailingProperties(
    val enabled: Boolean,
    val host: String,
    val port: Int,
    val protocol: String,
    val encoding: String,
    val username: String,
    val password: String,
    val address: String,
    val properties: Map<String, String> = mapOf()
)
