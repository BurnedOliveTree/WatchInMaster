package pw.edu.watchin.server.repository.video;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import pw.edu.watchin.server.domain.video.VideoEntity;
import pw.edu.watchin.server.security.Account;

import java.util.Optional;
import java.util.UUID;

public interface VideoRepositoryCustom {

    Optional<VideoEntity> findProcessedByIdWithFilterEnabled(UUID id, @Nullable Account account);

    Page<VideoEntity> findPublicVideosWithFilterEnabled(Pageable pageable, @Nullable Account account);

    Page<VideoEntity> findRelatedVideosWithFilterEnabled(UUID id, Pageable pageable, @Nullable Account account);

    Page<VideoEntity> findFromSubscribedChannelsWithFilterEnabled(Pageable pageable, Account account);

    Page<VideoEntity> findFavoriteVideosWithFilterEnabled(Pageable pageable, Account account);

    Page<VideoEntity> findWatchLaterVideosWithFilterEnabled(Pageable pageable, Account account);
}
