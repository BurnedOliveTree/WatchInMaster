package pw.edu.watchin.objectstorage.dto

data class RemoteResourceMetadata(
    val contentType: String,
    val contentLength: Long,
    val contentRange: String?
)
