package pw.edu.watchin.server.repository.video;

import org.springframework.data.repository.CrudRepository;
import pw.edu.watchin.server.domain.video.VideoTranscodingHash;

import java.util.UUID;

public interface VideoTranscodingRepository extends CrudRepository<VideoTranscodingHash, UUID> {
}
