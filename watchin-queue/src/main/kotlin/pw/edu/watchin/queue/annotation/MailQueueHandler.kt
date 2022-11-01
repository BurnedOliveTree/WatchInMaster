package pw.edu.watchin.queue.annotation

import org.springframework.amqp.rabbit.annotation.RabbitListener

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@RabbitListener(queues = ["#{beanFactory.getBean(T(pw.edu.watchin.queue.properties.QueueProperties)).mailQueue}"])
annotation class MailQueueHandler
