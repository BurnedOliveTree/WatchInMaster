package pw.edu.watchin.server.service.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pw.edu.watchin.media.dto.MediaResource;
import pw.edu.watchin.objectstorage.repository.AvatarObjectStorageRepository;
import pw.edu.watchin.objectstorage.repository.BackgroundObjectStorageRepository;
import pw.edu.watchin.objectstorage.repository.ThumbnailObjectStorageRepository;
import pw.edu.watchin.objectstorage.repository.VideoObjectStorageRepository;
import pw.edu.watchin.server.domain.resource.ResourceEntity;
import pw.edu.watchin.server.domain.resource.ResourceType;
import pw.edu.watchin.server.repository.resource.ResourceRepository;

import java.util.function.Consumer;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private AvatarObjectStorageRepository avatarObjectStorageRepository;

    @Autowired
    private BackgroundObjectStorageRepository backgroundObjectStorageRepository;

    @Autowired
    private ThumbnailObjectStorageRepository thumbnailObjectStorageRepository;

    @Autowired
    private VideoObjectStorageRepository videoObjectStorageRepository;

    @Transactional
    public ResourceEntity saveAvatar(MediaResource mediaResource) {
        var resource = new ResourceEntity();
        resource.setFileSize(mediaResource.getFileSize());
        resource.setFileContentType(mediaResource.getFileType());
        resource.setType(ResourceType.AVATAR);
        resourceRepository.save(resource);
        avatarObjectStorageRepository.uploadAvatar(
            mediaResource.getData(),
            mediaResource.getFileSize(),
            mediaResource.getFileType(),
            resource.getId().toString()
        );
        return resource;
    }

    @Transactional
    public ResourceEntity saveBackground(MediaResource mediaResource) {
        var resource = new ResourceEntity();
        resource.setFileSize(mediaResource.getFileSize());
        resource.setFileContentType(mediaResource.getFileType());
        resource.setType(ResourceType.BACKGROUND);
        resourceRepository.save(resource);
        backgroundObjectStorageRepository.uploadBackground(
            mediaResource.getData(),
            mediaResource.getFileSize(),
            mediaResource.getFileType(),
            resource.getId().toString()
        );
        return resource;
    }

    @Transactional
    public ResourceEntity saveThumbnail(MediaResource mediaResource) {
        var resource = new ResourceEntity();
        resource.setFileSize(mediaResource.getFileSize());
        resource.setFileContentType(mediaResource.getFileType());
        resource.setType(ResourceType.THUMBNAIL);
        resourceRepository.save(resource);
        thumbnailObjectStorageRepository.uploadThumbnail(
            mediaResource.getData(),
            mediaResource.getFileSize(),
            mediaResource.getFileType(),
            resource.getId().toString()
        );
        return resource;
    }

    @Transactional
    public ResourceEntity saveVideo(MediaResource mediaResource) {
        var resource = new ResourceEntity();
        resource.setFileSize(mediaResource.getFileSize());
        resource.setFileContentType(mediaResource.getFileType());
        resource.setType(ResourceType.VIDEO);
        resourceRepository.save(resource);
        videoObjectStorageRepository.uploadVideo(
            mediaResource.getData(),
            mediaResource.getFileSize(),
            mediaResource.getFileType(),
            resource.getId().toString()
        );
        return resource;
    }

    @Transactional
    public void purgeOrphans() {
        var orphans = resourceRepository.findOrphanedResources();
        orphans.forEach(orphan -> getDeletionDelegate(orphan.getType()).accept(orphan.getId().toString()));
        resourceRepository.deleteAll(orphans);
    }

    private Consumer<String> getDeletionDelegate(ResourceType type) {
        Consumer<String> value = null;
        switch (type) {
            case VIDEO: value = videoObjectStorageRepository::deleteVideo; break;
            case AVATAR: value = avatarObjectStorageRepository::deleteAvatar; break;
            case BACKGROUND: value = backgroundObjectStorageRepository::deleteBackground; break;
            case THUMBNAIL: value = thumbnailObjectStorageRepository::deleteThumbnail;
        }
        return value;
    }
}
