package pw.edu.watchin.common.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("filesystem")
data class FileSystemProperties(
    val videoSourceFilePrefix: String,
    val videoTranscodingFilePrefix: String,
    val thumbnailFilePrefix: String
)