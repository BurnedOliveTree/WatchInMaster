package pw.edu.watchin.mailing.service

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import pw.edu.watchin.mailing.dto.ProcessedMailData
import pw.edu.watchin.mailing.properties.MailingProperties

@Service
class MailSenderService(
    private val mailSender: JavaMailSender,
    private val mailingProperties: MailingProperties
) {

    fun sendEmail(processedMailData: ProcessedMailData) {
        if (!mailingProperties.enabled) {
            return
        }

        mailSender.createMimeMessage().let {
            MimeMessageHelper(it, true).run {
                setTo(processedMailData.recipient)
                setFrom(mailingProperties.address)
                setSubject(processedMailData.subject)
                setText(processedMailData.content, true)
            }
            mailSender.send(it)
        }
    }
}
