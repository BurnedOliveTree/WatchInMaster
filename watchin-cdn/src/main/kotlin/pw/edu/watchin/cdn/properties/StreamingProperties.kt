package pw.edu.watchin.cdn.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.util.unit.DataSize

@ConstructorBinding
@ConfigurationProperties("streaming")
data class StreamingProperties(
    val maxChunkSize: DataSize
)
