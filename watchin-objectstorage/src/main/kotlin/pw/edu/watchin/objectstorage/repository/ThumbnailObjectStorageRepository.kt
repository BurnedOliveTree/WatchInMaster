package pw.edu.watchin.objectstorage.repository

import pw.edu.watchin.objectstorage.annotation.ObjectStorageRepository
import pw.edu.watchin.objectstorage.dto.RemoteStreamingResource
import reactor.core.publisher.Mono
import java.io.InputStream

@ObjectStorageRepository("thumbnail")
class ThumbnailObjectStorageRepository : AbstractObjectStorageRepository() {

    fun uploadThumbnail(data: InputStream, fileSize: Long, fileType: String, fileKey: String) {
        putObject(data, fileSize, fileType, fileKey)
    }

    fun deleteThumbnail(fileKey: String) {
        deleteObject(fileKey)
    }

    fun streamThumbnail(fileKey: String): Mono<RemoteStreamingResource> {
        return streamObject(fileKey)
    }
}
