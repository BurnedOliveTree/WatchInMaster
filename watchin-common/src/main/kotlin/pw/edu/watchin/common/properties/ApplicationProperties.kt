package pw.edu.watchin.common.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("application")
data class ApplicationProperties(
    val frontend: FrontendProperties,
    val cdn: CDNProperties,
    val deepLinking: DeepLinkingProperties
) {
    data class FrontendProperties(
        val url: String,
        val rasterLogoPath: String,
        val vectorLogoPath: String
    )

    data class CDNProperties(
        val url: String,
        val avatarPath: String,
        val backgroundPath: String,
        val thumbnailPath: String,
        val videoPath: String
    )

    data class DeepLinkingProperties(
        val activation: String,
        val passwordReset: String,
        val video: String
    )
}
