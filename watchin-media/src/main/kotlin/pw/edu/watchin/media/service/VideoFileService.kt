package pw.edu.watchin.media.service

import org.springframework.stereotype.Service
import pw.edu.watchin.common.properties.FileSystemProperties
import pw.edu.watchin.media.dto.MediaResource
import pw.edu.watchin.media.properties.MultimediaProperties
import ws.schild.jave.Encoder
import ws.schild.jave.MultimediaObject
import ws.schild.jave.ScreenExtractor
import ws.schild.jave.encode.AudioAttributes
import ws.schild.jave.encode.EncodingAttributes
import ws.schild.jave.encode.VideoAttributes
import ws.schild.jave.info.VideoSize
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import java.time.Duration

@Service
class VideoFileService(
    private val multimediaProperties: MultimediaProperties,
    private val fileSystemProperties: FileSystemProperties
) {

    fun getDuration(file: File): Duration {
        return Duration.ofMillis(MultimediaObject(file).info.duration)
    }

    fun getResolution(file: File): Pair<Int, Int> {
        return MultimediaObject(file).info.video.size.run {
            Pair(width, height)
        }
    }

    fun generateThumbnail(file: File): MediaResource {
        val targetFile = File.createTempFile(fileSystemProperties.thumbnailFilePrefix, ".jpg").also {
            it.deleteOnExit()
        }

        MultimediaObject(file).run {
            ScreenExtractor().renderOneImage(
                this,
                multimediaProperties.thumbnail.width,
                multimediaProperties.thumbnail.height,
                this.info.duration / 2,
                targetFile,
                0
            )
        }

        return targetFile.readBytes()
            .let { MediaResource(ByteArrayInputStream(it), it.size.toLong(), "image/jpeg") }
            .also { targetFile.delete() }
    }

    fun transcode(source: File, target: File, width: Int, height: Int): MediaResource {
        return EncodingAttributes().apply {
            setOutputFormat(multimediaProperties.video.outputFormat)
            setAudioAttributes(AudioAttributes().apply {
                setCodec(multimediaProperties.video.audioCodec)
                setBitRate(multimediaProperties.video.audioBitRate)
                setSamplingRate(multimediaProperties.video.audioSamplingRate)
                setChannels(multimediaProperties.video.audioChannels)
            })
            setVideoAttributes(VideoAttributes().apply {
                setCodec(multimediaProperties.video.videoCodec)
                setFrameRate(multimediaProperties.video.videoFrameRate)
                setSize(VideoSize(width, height))
            })
        }
        .let { Encoder().encode(MultimediaObject(source), target, it) }
        .let { MediaResource(FileInputStream(target), Files.size(target.toPath()), multimediaProperties.video.contentType) }
    }
}