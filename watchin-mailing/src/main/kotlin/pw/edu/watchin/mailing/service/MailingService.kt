package pw.edu.watchin.mailing.service

import pw.edu.watchin.mailing.annotation.MailRecipient
import pw.edu.watchin.mailing.annotation.MailTemplate
import pw.edu.watchin.mailing.annotation.MailVariable

interface MailingService {

    @MailTemplate("registration")
    fun sendRegistrationEmail(
        @MailRecipient recipient: String,
        @MailVariable("username") username: String,
        @MailVariable("activationLink") activationLink: String
    )

    @MailTemplate("password-reset")
    fun sendPasswordResetEmail(
        @MailRecipient recipient: String,
        @MailVariable("username") username: String,
        @MailVariable("passwordResetLink") passwordResetLink: String
    )

    @MailTemplate("video-ready")
    fun sendVideoReadyEmail(
        @MailRecipient recipient: String,
        @MailVariable("username") username: String,
        @MailVariable("videoTitle") videoTitle: String,
        @MailVariable("videoLink") videoLink: String
    )
}
