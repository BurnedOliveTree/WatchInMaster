package pw.edu.watchin.objectstorage.repository

import pw.edu.watchin.objectstorage.annotation.ObjectStorageRepository
import pw.edu.watchin.objectstorage.dto.RemoteStreamingResource
import reactor.core.publisher.Mono
import java.io.InputStream

@ObjectStorageRepository("avatar")
class AvatarObjectStorageRepository : AbstractObjectStorageRepository() {

    fun uploadAvatar(data: InputStream, fileSize: Long, fileType: String, fileKey: String) {
        putObject(data, fileSize, fileType, fileKey)
    }

    fun deleteAvatar(fileKey: String) {
        deleteObject(fileKey)
    }

    fun streamAvatar(fileKey: String): Mono<RemoteStreamingResource> {
        return streamObject(fileKey)
    }
}
