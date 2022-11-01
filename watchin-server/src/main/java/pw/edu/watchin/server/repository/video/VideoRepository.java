package pw.edu.watchin.server.repository.video;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pw.edu.watchin.server.domain.video.VideoEntity;
import pw.edu.watchin.server.domain.video.VideoStatusType;

import java.util.Optional;
import java.util.UUID;

public interface VideoRepository extends JpaRepository<VideoEntity, UUID>, VideoRepositoryCustom {

    @Query("select video from VideoEntity video " +
        "where video.id = :videoId and " +
        "video.status in (pw.edu.watchin.server.domain.video.VideoStatusType.PARTIALLY_READY, pw.edu.watchin.server.domain.video.VideoStatusType.READY)")
    Optional<VideoEntity> findProcessedById(@Param("videoId") UUID videoId);

    @Query("select video from VideoEntity video " +
        "where video.visibility = pw.edu.watchin.server.domain.video.VideoVisibilityType.PUBLIC " +
        "and video.status in (pw.edu.watchin.server.domain.video.VideoStatusType.PARTIALLY_READY, pw.edu.watchin.server.domain.video.VideoStatusType.READY)")
    Page<VideoEntity> findPublicVideos(Pageable pageable);

    @Query(nativeQuery = true, value =
        "select video.* from video " +
        "join video current_video on current_video.id = :videoId " +
        "where video.visibility = 'PUBLIC' " +
        "and video.status in ('PARTIALLY_READY', 'READY') " +
        "and video.id <> :videoId " +
        "order by similarity(current_video.title || ' ' || coalesce(current_video.description, ''), video.title || ' ' || coalesce(video.description, '')) desc, " +
        "video.views desc")
    Page<VideoEntity> findRelatedVideos(@Param("videoId") UUID videoId, Pageable pageable);

    @Query("select video from VideoEntity video " +
        "join video.channel.subscribedBy subscribed " +
        "where subscribed.subscriber.accountId = :accountId " +
        "and video.visibility = pw.edu.watchin.server.domain.video.VideoVisibilityType.PUBLIC " +
        "and video.status in (pw.edu.watchin.server.domain.video.VideoStatusType.PARTIALLY_READY, pw.edu.watchin.server.domain.video.VideoStatusType.READY) " +
        "order by video.uploaded desc")
    Page<VideoEntity> findFromSubscribedChannels(@Param("accountId") Integer accountId, Pageable pageable);

    @Query("select video from VideoEntity video " +
        "join video.favorites favorite " +
        "where favorite.channel.accountId = :accountId " +
        "and video.visibility = pw.edu.watchin.server.domain.video.VideoVisibilityType.PUBLIC " +
        "and video.status in (pw.edu.watchin.server.domain.video.VideoStatusType.PARTIALLY_READY, pw.edu.watchin.server.domain.video.VideoStatusType.READY) " +
        "order by favorite.id desc")
    Page<VideoEntity> findFavoriteVideos(@Param("accountId") Integer accountId, Pageable pageable);

    @Query("select video from VideoEntity video " +
        "join video.watchLaterList watchLater " +
        "where watchLater.channel.accountId = :accountId " +
        "and video.visibility = pw.edu.watchin.server.domain.video.VideoVisibilityType.PUBLIC " +
        "and video.status in (pw.edu.watchin.server.domain.video.VideoStatusType.PARTIALLY_READY, pw.edu.watchin.server.domain.video.VideoStatusType.READY) " +
        "order by watchLater.id desc")
    Page<VideoEntity> findWatchLaterVideos(@Param("accountId") Integer accountId, Pageable pageable);

    Page<VideoEntity> findByChannelAccountIdAndTitleContainsIgnoreCase(Integer accountId, String phrase, Pageable pageable);

    @Modifying
    @Query("update VideoEntity set views = views + 1 where id = :videoId")
    void updateViewCounter(@Param("videoId") UUID videoId);
}
