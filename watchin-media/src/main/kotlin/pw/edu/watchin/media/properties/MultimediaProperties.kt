package pw.edu.watchin.media.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("multimedia")
data class MultimediaProperties(
    val video: VideoProperties,
    val thumbnail: ThumbnailProperties,
    val avatar: AvatarProperties
) {
    data class VideoProperties(
        val contentType: String,
        val outputFormat: String,
        val audioCodec: String,
        val audioBitRate: Int,
        val audioSamplingRate: Int,
        val audioChannels: Int,
        val videoCodec: String,
        val videoFrameRate: Int
    )

    data class ThumbnailProperties(
        val width: Int,
        val height: Int
    )

    data class AvatarProperties(
        val size: Int,
        val fontSize: Float,
        val fontName: String
    )
}