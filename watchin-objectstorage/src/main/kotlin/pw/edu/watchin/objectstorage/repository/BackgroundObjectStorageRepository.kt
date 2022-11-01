package pw.edu.watchin.objectstorage.repository

import pw.edu.watchin.objectstorage.annotation.ObjectStorageRepository
import pw.edu.watchin.objectstorage.dto.RemoteStreamingResource
import reactor.core.publisher.Mono
import java.io.InputStream

@ObjectStorageRepository("background")
class BackgroundObjectStorageRepository : AbstractObjectStorageRepository() {

    fun uploadBackground(data: InputStream, fileSize: Long, fileType: String, fileKey: String) {
        putObject(data, fileSize, fileType, fileKey)
    }

    fun deleteBackground(fileKey: String) {
        deleteObject(fileKey)
    }

    fun streamBackground(fileKey: String): Mono<RemoteStreamingResource> {
        return streamObject(fileKey)
    }
}
