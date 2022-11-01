package pw.edu.watchin.media.service

import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import pw.edu.watchin.media.dto.MediaResource
import pw.edu.watchin.media.properties.MultimediaProperties
import pw.edu.watchin.media.utils.ColorScheme
import java.awt.Color
import java.awt.Font
import java.awt.Image
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.imageio.IIOImage
import javax.imageio.ImageIO
import javax.imageio.ImageWriteParam
import javax.imageio.stream.MemoryCacheImageOutputStream

@Service
class ImageFileService(
    private val multimediaProperties: MultimediaProperties
) {

    fun generateAvatarFromUsername(username: String): MediaResource {
        val image = BufferedImage(multimediaProperties.avatar.size, multimediaProperties.avatar.size, BufferedImage.TYPE_INT_RGB)
        image.createGraphics().apply {
            setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
            setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
            font = Font.createFont(
                Font.TRUETYPE_FONT,
                ClassPathResource(multimediaProperties.avatar.fontName).inputStream
            ).deriveFont(multimediaProperties.avatar.fontSize)
            val text = username.first().toString().toUpperCase()
            val metrics = getFontMetrics(font)
            val colorScheme = ColorScheme.getRandomColorScheme()
            color = Color.decode(colorScheme.first)
            fillRect(0, 0, image.width, image.height)
            color = Color.decode(colorScheme.second)
            drawString(text, (image.width - metrics.stringWidth(text)) / 2, (image.height - metrics.height) / 2 + metrics.ascent)
        }
        return writeImage(image)
    }

    fun handleUploadedAvatar(inputStream: InputStream): MediaResource {
        val image = BufferedImage(multimediaProperties.avatar.size, multimediaProperties.avatar.size, BufferedImage.TYPE_INT_RGB)
        image.createGraphics().apply {
            drawImage(ImageIO.read(inputStream).getScaledInstance(multimediaProperties.avatar.size, multimediaProperties.avatar.size, Image.SCALE_SMOOTH), 0, 0, null)
        }
        return writeImage(image)
    }

    fun handleUploadedBackground(inputStream: InputStream): MediaResource {
        return writeImage(ImageIO.read(inputStream))
    }

    fun handleUploadedThumbnail(inputStream: InputStream): MediaResource {
        return writeImage(ImageIO.read(inputStream))
    }

    private fun writeImage(bufferedImage: BufferedImage): MediaResource {
        val outputStream = ByteArrayOutputStream()

        ImageIO.getImageWritersByFormatName("jpg").next().apply {
            output = MemoryCacheImageOutputStream(outputStream)
            write(null, IIOImage(bufferedImage, null, null), defaultWriteParam.apply {
                compressionMode = ImageWriteParam.MODE_EXPLICIT
                compressionQuality = 1.0f
            })
        }

        return outputStream
            .toByteArray()
            .let { MediaResource(ByteArrayInputStream(it), it.size.toLong(), "image/jpeg") }
            .also { outputStream.close() }
    }
}