package pw.edu.watchin.server.service.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import pw.edu.watchin.common.properties.ApplicationProperties;
import pw.edu.watchin.server.domain.resource.ResourceEntity;
import pw.edu.watchin.server.dto.resource.ResourceDTO;

import java.util.Optional;

@Service
public class ResourceMapperService {

    @Autowired
    private ApplicationProperties applicationProperties;

    public ResourceDTO getResourceLocation(@Nullable ResourceEntity resource) {
        return Optional.ofNullable(resource)
            .map(__ -> {
                String value = "";
                switch (resource.getType()) {
                    case VIDEO: value = applicationProperties.getCdn().getVideoPath(); break;
                    case AVATAR: value = applicationProperties.getCdn().getAvatarPath(); break;
                    case BACKGROUND: value = applicationProperties.getCdn().getBackgroundPath(); break;
                    case THUMBNAIL: value = applicationProperties.getCdn().getThumbnailPath();
                }
                return value;
            })
            .map(path -> applicationProperties.getCdn().getUrl() + path + resource.getId())
            .map(location -> new ResourceDTO(location, resource.getType()))
            .orElse(null);
    }
}
