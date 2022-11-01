package pw.edu.watchin.queue.config

import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pw.edu.watchin.queue.properties.QueueProperties

@Configuration
class QueueConfig {

    @Bean
    fun rabbitTemplate(configurer: RabbitTemplateConfigurer, connectionFactory: ConnectionFactory): RabbitTemplate {
        return RabbitTemplate().apply {
            configurer.configure(this, connectionFactory)
            isChannelTransacted = true
        }
    }

    @Bean
    fun mailQueue(queueProperties: QueueProperties): Queue {
        return Queue(queueProperties.mailQueue)
    }

    @Bean
    fun videoTranscodingQueue(queueProperties: QueueProperties): Queue {
        return Queue(queueProperties.videoTranscodingQueue)
    }
}