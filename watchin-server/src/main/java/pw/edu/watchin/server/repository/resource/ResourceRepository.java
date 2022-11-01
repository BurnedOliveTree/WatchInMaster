package pw.edu.watchin.server.repository.resource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pw.edu.watchin.server.domain.resource.ResourceEntity;

import java.util.List;
import java.util.UUID;

public interface ResourceRepository extends JpaRepository<ResourceEntity, UUID> {

    @Query(nativeQuery = true, value = "select * from f_orphans(cast(null as resource))")
    List<ResourceEntity> findOrphanedResources();
}
