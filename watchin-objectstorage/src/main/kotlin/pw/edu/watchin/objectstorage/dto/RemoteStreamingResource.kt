package pw.edu.watchin.objectstorage.dto

import reactor.core.publisher.Flux
import java.nio.ByteBuffer

data class RemoteStreamingResource(
    val byteBuffer: Flux<ByteBuffer>,
    val metadata: RemoteResourceMetadata
)
