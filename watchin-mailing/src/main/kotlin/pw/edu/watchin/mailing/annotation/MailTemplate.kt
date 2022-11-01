package pw.edu.watchin.mailing.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class MailTemplate(val template: String)
