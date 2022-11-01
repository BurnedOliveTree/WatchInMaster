package pw.edu.watchin.mailing.dto

import java.io.Serializable

data class MailParameters(
    val template: String,
    val recipient: String,
    val parameters: Map<String, Any?>
) : Serializable
