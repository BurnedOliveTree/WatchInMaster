package pw.edu.watchin.objectstorage.async

import pw.edu.watchin.objectstorage.dto.RemoteResourceMetadata
import pw.edu.watchin.objectstorage.dto.RemoteStreamingResource
import reactor.core.publisher.Flux
import software.amazon.awssdk.core.async.AsyncResponseTransformer
import software.amazon.awssdk.core.async.SdkPublisher
import software.amazon.awssdk.services.s3.model.GetObjectResponse
import java.nio.ByteBuffer
import java.util.concurrent.CompletableFuture

class AsyncResponseProvider : AsyncResponseTransformer<GetObjectResponse, RemoteStreamingResource> {

    private val future = CompletableFuture<RemoteStreamingResource>()

    private lateinit var metadata: RemoteResourceMetadata

    override fun prepare(): CompletableFuture<RemoteStreamingResource> {
        return future
    }

    override fun onResponse(response: GetObjectResponse) {
        metadata = RemoteResourceMetadata(
            response.contentType(),
            response.contentLength(),
            response.contentRange()
        )
    }

    override fun onStream(publisher: SdkPublisher<ByteBuffer>) {
        future.complete(RemoteStreamingResource(Flux.from(publisher), metadata))
    }

    override fun exceptionOccurred(error: Throwable) {
        future.completeExceptionally(error)
    }
}
