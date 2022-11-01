package pw.edu.watchin.mailing.annotation

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class MailVariable(val name: String)
