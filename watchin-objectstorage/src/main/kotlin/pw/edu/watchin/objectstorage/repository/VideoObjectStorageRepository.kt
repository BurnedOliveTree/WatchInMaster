package pw.edu.watchin.objectstorage.repository

import pw.edu.watchin.objectstorage.annotation.ObjectStorageRepository
import pw.edu.watchin.objectstorage.dto.RemoteStreamingResource
import reactor.core.publisher.Mono
import java.io.InputStream

@ObjectStorageRepository("video")
class VideoObjectStorageRepository : AbstractObjectStorageRepository() {

    fun uploadVideo(data: InputStream, fileSize: Long, fileType: String, fileKey: String) {
        putObject(data, fileSize, fileType, fileKey)
    }

    fun deleteVideo(fileKey: String) {
        deleteObject(fileKey)
    }

    fun streamVideo(videoKey: String, rangeStart: Long, rangeEnd: Long): Mono<RemoteStreamingResource> {
        return streamObject(videoKey, rangeStart, rangeEnd)
    }
}
