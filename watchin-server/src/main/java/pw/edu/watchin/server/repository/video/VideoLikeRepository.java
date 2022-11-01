package pw.edu.watchin.server.repository.video;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pw.edu.watchin.server.domain.video.VideoLikeEntity;
import pw.edu.watchin.server.dto.video.VideoLikesDTO;

import java.util.UUID;
import java.util.Optional;

public interface VideoLikeRepository extends JpaRepository<VideoLikeEntity, Integer> {

    @Query("select new pw.edu.watchin.server.dto.video.VideoLikesDTO( " +
        "count(case when liked = true then 1 end), " +
        "count(case when liked = false then 1 end), " +
        "coalesce((select liked from VideoLikeEntity where video.id = :videoId and channel.accountId = :accountId), false), " +
        "coalesce((select case when liked = false then true else false end from VideoLikeEntity where video.id = :videoId and channel.accountId = :accountId), false)) " +
        "from VideoLikeEntity " +
        "where video.id = :videoId")
    VideoLikesDTO getLikeStatisticsForVideoId(@Param("videoId") UUID videoId, @Param("accountId") Integer accountId);

    void deleteByVideoIdAndChannelAccountIdAndLiked(UUID videoId, Integer accountId, boolean liked);

    Optional<VideoLikeEntity> findByVideoIdAndChannelAccountId(UUID videoId, Integer accountId);
}
