package pw.edu.watchin.server.repository.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import pw.edu.watchin.server.domain.video.VideoEntity;
import pw.edu.watchin.server.security.Account;

import java.util.Optional;
import java.util.UUID;

public class VideoRepositoryCustomImpl extends BaseVideoRepositoryWithFilter implements VideoRepositoryCustom {

    @Autowired
    @Lazy
    private VideoRepository videoRepository;

    @Override
    public Optional<VideoEntity> findProcessedByIdWithFilterEnabled(UUID id, @Nullable Account account) {
        enableFilter(account);
        return videoRepository.findProcessedById(id);
    }

    @Override
    public Page<VideoEntity> findPublicVideosWithFilterEnabled(Pageable pageable, @Nullable Account account) {
        enableFilter(account);
        return videoRepository.findPublicVideos(pageable);
    }

    @Override
    public Page<VideoEntity> findRelatedVideosWithFilterEnabled(UUID id, Pageable pageable, Account account) {
        enableFilter(account);
        return videoRepository.findRelatedVideos(id, pageable);
    }

    @Override
    public Page<VideoEntity> findFromSubscribedChannelsWithFilterEnabled(Pageable pageable, Account account) {
        enableFilter(account);
        return videoRepository.findFromSubscribedChannels(account.getId(), pageable);
    }

    @Override
    public Page<VideoEntity> findFavoriteVideosWithFilterEnabled(Pageable pageable, Account account) {
        enableFilter(account);
        return videoRepository.findFavoriteVideos(account.getId(), pageable);
    }

    @Override
    public Page<VideoEntity> findWatchLaterVideosWithFilterEnabled(Pageable pageable, Account account) {
        enableFilter(account);
        return videoRepository.findWatchLaterVideos(account.getId(), pageable);
    }
}
