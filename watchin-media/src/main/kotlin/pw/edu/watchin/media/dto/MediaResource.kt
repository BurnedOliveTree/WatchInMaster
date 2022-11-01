package pw.edu.watchin.media.dto

import java.io.InputStream

data class MediaResource(
    val data: InputStream,
    val fileSize: Long,
    val fileType: String
)
