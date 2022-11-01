package pw.edu.watchin.mailing.service

import com.googlecode.htmlcompressor.compressor.HtmlCompressor
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import pw.edu.watchin.common.properties.ApplicationProperties
import pw.edu.watchin.mailing.dto.MailParameters
import pw.edu.watchin.mailing.dto.ProcessedMailData
import pw.edu.watchin.mailing.extension.setApplicationVariables

@Service
class MailTemplatingService(
    private val applicationProperties: ApplicationProperties,
    private val templateEngine: TemplateEngine,
    private val htmlCompressor: HtmlCompressor
) {

    fun process(parameters: MailParameters): ProcessedMailData {
        val context = Context().apply {
            setVariables(parameters.parameters)
            setApplicationVariables(applicationProperties)
        }

        val subject = templateEngine.process(parameters.template, setOf("//title//text()"), context).trim()

        val content = templateEngine.process(parameters.template, context)
            .let { htmlCompressor.compress(it) }

        return ProcessedMailData(parameters.recipient, subject, content)
    }
}