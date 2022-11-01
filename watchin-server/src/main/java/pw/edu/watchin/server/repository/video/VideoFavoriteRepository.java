package pw.edu.watchin.server.repository.video;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.edu.watchin.server.domain.video.VideoFavoriteEntity;

import java.util.UUID;

public interface VideoFavoriteRepository extends JpaRepository<VideoFavoriteEntity, Integer> {

    void deleteByVideoIdAndChannelAccountId(UUID videoId, Integer accountId);
}
