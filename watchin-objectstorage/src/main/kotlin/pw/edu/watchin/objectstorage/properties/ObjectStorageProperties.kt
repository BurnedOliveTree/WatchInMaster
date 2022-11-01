package pw.edu.watchin.objectstorage.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.net.URI

@ConstructorBinding
@ConfigurationProperties("object-storage")
data class ObjectStorageProperties(
    val location: URI,
    val accessKey: String,
    val secretKey: String,
    val bucketName: String,
    val region: String
)
