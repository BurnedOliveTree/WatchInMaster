package pw.edu.watchin.queue.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("queue")
data class QueueProperties(
    val mailQueue: String,
    val videoTranscodingQueue: String
)
