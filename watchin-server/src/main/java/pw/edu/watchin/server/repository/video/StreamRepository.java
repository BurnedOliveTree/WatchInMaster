package pw.edu.watchin.server.repository.video;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pw.edu.watchin.server.domain.video.StreamEntity;

import java.util.Optional;
import java.util.UUID;

public interface StreamRepository extends JpaRepository<StreamEntity, UUID> {
    @Override
    Optional<StreamEntity> findById(UUID uuid);

    @Modifying
    @Query("update StreamEntity set views = views + 1 where id = ?1")
    void updateViewCounter(UUID id);
}
