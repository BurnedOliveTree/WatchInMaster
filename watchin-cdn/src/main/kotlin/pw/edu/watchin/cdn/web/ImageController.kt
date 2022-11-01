package pw.edu.watchin.cdn.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pw.edu.watchin.objectstorage.dto.RemoteStreamingResource
import pw.edu.watchin.objectstorage.repository.AvatarObjectStorageRepository
import pw.edu.watchin.objectstorage.repository.BackgroundObjectStorageRepository
import pw.edu.watchin.objectstorage.repository.ThumbnailObjectStorageRepository
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/cdn/image")
class ImageController(
    private val avatarObjectStorageRepository: AvatarObjectStorageRepository,
    private val backgroundObjectStorageRepository: BackgroundObjectStorageRepository,
    private val thumbnailObjectStorageRepository: ThumbnailObjectStorageRepository
) {

    @GetMapping("/avatar/{fileKey}")
    fun getAvatar(@PathVariable fileKey: String): Mono<RemoteStreamingResource> {
        return avatarObjectStorageRepository.streamAvatar(fileKey)
    }

    @GetMapping("/background/{fileKey}")
    fun getBackground(@PathVariable fileKey: String): Mono<RemoteStreamingResource> {
        return backgroundObjectStorageRepository.streamBackground(fileKey)
    }

    @GetMapping("/thumbnail/{fileKey}")
    fun getThumbnail(@PathVariable fileKey: String): Mono<RemoteStreamingResource> {
        return thumbnailObjectStorageRepository.streamThumbnail(fileKey)
    }
}