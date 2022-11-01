package pw.edu.watchin.queue.template

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import pw.edu.watchin.queue.properties.QueueProperties
import java.util.*

@Service
class QueueTemplate(
    private val queueProperties: QueueProperties,
    private val rabbitTemplate: RabbitTemplate
) {
    fun enqueueMail(mailParameters: Any) {
        rabbitTemplate.convertAndSend(queueProperties.mailQueue, mailParameters)
    }

    fun enqueueVideoTranscoding(transcodingProcessId: UUID) {
        rabbitTemplate.convertAndSend(queueProperties.videoTranscodingQueue, transcodingProcessId)
    }
}