package pw.edu.watchin.cdn.codec

import org.reactivestreams.Publisher
import org.springframework.core.ResolvableType
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ReactiveHttpOutputMessage
import org.springframework.http.codec.HttpMessageWriter
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import pw.edu.watchin.objectstorage.dto.RemoteStreamingResource
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.nio.ByteBuffer

class RemoteStreamingResourceMessageWriter : HttpMessageWriter<RemoteStreamingResource> {

    override fun canWrite(elementType: ResolvableType, mediaType: MediaType?): Boolean =
        elementType.isAssignableFrom(RemoteStreamingResource::class.java)

    override fun getWritableMediaTypes(): List<MediaType> = listOf()

    override fun write(input: Publisher<out RemoteStreamingResource>,
                       elementType: ResolvableType,
                       mediaType: MediaType?,
                       message: ReactiveHttpOutputMessage,
                       hints: Map<String, Any>
    ): Mono<Void> = throw UnsupportedOperationException()

    override fun write(input: Publisher<out RemoteStreamingResource>,
                       actualType: ResolvableType,
                       elementType: ResolvableType,
                       mediaType: MediaType?,
                       request: ServerHttpRequest,
                       response: ServerHttpResponse,
                       hints: Map<String, Any>
    ): Mono<Void> {
        return input.toMono()
            .flatMapMany { prepareResponse(it, response) }
            .map { response.bufferFactory().wrap(it) }
            .let { response.writeWith(it) }
    }

    private fun prepareResponse(resource: RemoteStreamingResource, response: ServerHttpResponse): Flux<ByteBuffer> {
        val isRanged = resource.metadata.contentRange != null
        response.headers.run {
            contentType = MediaType.parseMediaType(resource.metadata.contentType)
            contentLength = resource.metadata.contentLength
            if (isRanged) {
                set(HttpHeaders.CONTENT_RANGE, resource.metadata.contentRange)
                set(HttpHeaders.ACCEPT_RANGES, "bytes")
            }
        }
        response.statusCode = if (isRanged) HttpStatus.PARTIAL_CONTENT else HttpStatus.OK
        return resource.byteBuffer
    }
}
