package pw.edu.watchin.mailing.dto

data class ProcessedMailData(
    val recipient: String,
    val subject: String,
    val content: String
)
