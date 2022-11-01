package pw.edu.watchin.server.repository.video;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pw.edu.watchin.server.domain.video.VideoCommentEntity;

import java.util.UUID;

public interface VideoCommentRepository extends JpaRepository<VideoCommentEntity, Integer> {

    Page<VideoCommentEntity> findByVideoIdOrderByCreationDateDesc(UUID videoId, Pageable pageable);
}
