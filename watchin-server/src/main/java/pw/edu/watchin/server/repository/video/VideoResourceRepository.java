package pw.edu.watchin.server.repository.video;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.edu.watchin.server.domain.video.VideoResourceEntity;

public interface VideoResourceRepository extends JpaRepository<VideoResourceEntity, Integer> {
}
