package pw.edu.watchin.server.repository.video;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.edu.watchin.server.domain.video.VideoWatchLaterEntity;

import java.util.UUID;

public interface VideoWatchLaterRepository extends JpaRepository<VideoWatchLaterEntity, Integer> {

    void deleteByVideoIdAndChannelAccountId(UUID videoId, Integer accountId);
}
