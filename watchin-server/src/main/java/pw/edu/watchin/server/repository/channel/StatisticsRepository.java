package pw.edu.watchin.server.repository.channel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pw.edu.watchin.server.domain.channel.StatisticsEntity;

import java.util.List;

public interface StatisticsRepository extends JpaRepository<StatisticsEntity, Integer> {

    @Modifying
    @Query(nativeQuery = true, value =
        "insert into statistics(channel_id, date, subscribers, videos, views, comments) " +
        "select channel.account_id, " +
        "current_timestamp, " +
        "(select count(*) from subscription where subscription.channel_id = channel.account_id), " +
        "(select count(*) from video where video.channel_id = channel.account_id), " +
        "coalesce((select sum(video.views) from video where video.channel_id = channel.account_id), 0), " +
        "(select count(*) from video_comment join video on video_comment.video_id = video.id where video.channel_id = channel.account_id) " +
        "from channel")
    void updateStatisticsForChannels();

    List<StatisticsEntity> findAllByChannelAccountIdOrderByDateAsc(Integer accountId);
}
