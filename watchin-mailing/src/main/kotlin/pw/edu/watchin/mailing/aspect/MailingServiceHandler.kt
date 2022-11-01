package pw.edu.watchin.mailing.aspect

import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.springframework.stereotype.Component
import pw.edu.watchin.mailing.annotation.MailRecipient
import pw.edu.watchin.mailing.annotation.MailTemplate
import pw.edu.watchin.mailing.annotation.MailVariable
import pw.edu.watchin.mailing.dto.MailParameters
import pw.edu.watchin.mailing.service.MailSenderService
import pw.edu.watchin.mailing.service.MailTemplatingService
import pw.edu.watchin.queue.annotation.MailQueueHandler
import pw.edu.watchin.queue.template.QueueTemplate
import java.lang.reflect.Method
import kotlin.reflect.KClass

@Component
class MailingServiceHandler(
    private val mailTemplatingService: MailTemplatingService,
    private val mailSenderService: MailSenderService,
    private val queueTemplate: QueueTemplate
) : MethodInterceptor {
    
    override fun invoke(invocation: MethodInvocation): Any? {
        val method = invocation.method
        val parameters = method.parameters
        val arguments = invocation.arguments

        val template = method.getAnnotation(MailTemplate::class.java)
            ?.template
            ?: throwMissingAnnotation(method, MailTemplate::class)

        val recipient = parameters
            .indexOfFirst { it.getAnnotation(MailRecipient::class.java) != null }
            .let { arguments.getOrNull(it) as? String }
            ?: throwMissingAnnotation(method, MailRecipient::class)

        val variables = parameters
            .mapIndexed { index, parameter -> Pair(parameter.getAnnotation(MailVariable::class.java), index) }
            .filter { (annotation, _) -> annotation != null }
            .associate { (annotation, index) -> Pair(annotation.name, arguments[index]) }

        queueTemplate.enqueueMail(MailParameters(template, recipient, variables))

        return Unit
    }

    @SuppressWarnings("unused")
    @MailQueueHandler
    fun queueHandler(mailParameters: MailParameters) {
        mailSenderService.sendEmail(mailTemplatingService.process(mailParameters))
    }

    private fun throwMissingAnnotation(method: Method, annotationClass: KClass<*>): Nothing {
        throw IllegalArgumentException("${method.name} is missing ${annotationClass.simpleName} annotation")
    }
}