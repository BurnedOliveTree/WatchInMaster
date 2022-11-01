package pw.edu.watchin.cdn.web

import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*
import pw.edu.watchin.cdn.properties.StreamingProperties
import pw.edu.watchin.objectstorage.dto.RemoteStreamingResource
import pw.edu.watchin.objectstorage.repository.VideoObjectStorageRepository
import reactor.core.publisher.Mono
import kotlin.math.min

@RestController
@RequestMapping("/cdn/video")
class VideoController(
    private val videoObjectStorageRepository: VideoObjectStorageRepository,
    private val streamingProperties: StreamingProperties
) {

    @GetMapping("/stream/{fileKey}")
    fun streamVideo(@PathVariable fileKey: String, @RequestHeader requestHeaders: HttpHeaders?): Mono<RemoteStreamingResource> {
        val contentLength = Long.MAX_VALUE
        val range = requestHeaders
            ?.range
            ?.firstOrNull()
            ?.let { Pair(it.getRangeStart(contentLength), it.getRangeEnd(contentLength)) }
            ?: Pair(0L, contentLength - 1)
        val truncatedRange = Pair(range.first, range.first + min(streamingProperties.maxChunkSize.toBytes() - 1, range.second - range.first))
        return videoObjectStorageRepository.streamVideo(fileKey, truncatedRange.first, truncatedRange.second)
    }
}
