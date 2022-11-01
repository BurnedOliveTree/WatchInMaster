package pw.edu.watchin.server.repository.video;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pw.edu.watchin.server.domain.video.VideoEntity;

import java.util.List;
import java.util.UUID;

public interface VideoSearchRepository extends JpaRepository<VideoEntity, UUID>, VideoSearchRepositoryCustom {

    @Query("select video.title from VideoEntity video where upper(video.title) like upper(concat(:phrase, '%')) order by video.views desc")
    List<String> getAutocompleteOptions(@Param("phrase") String phrase, Pageable pageable);

    @Query(nativeQuery = true, value =
        "select video.* from video " +
        "join account on video.channel_id = account.id " +
        "where visibility = 'PUBLIC' " +
        "and video.status in ('PARTIALLY_READY', 'READY') " +
        "and (:phrase is null or strict_word_similarity(cast(:phrase as text), title || ' ' || coalesce(description, '')) > 0.2) " +
        "and (:channel is null or account.username = cast(:channel as text)) " +
        "and (:dateFilter is null or uploaded >= now() - case " +
        "when :dateFilter = 'HOUR' then interval '1 hour' " +
        "when :dateFilter = 'DAY' then interval '1 day' " +
        "when :dateFilter = 'WEEK' then interval '1 week' " +
        "when :dateFilter = 'MONTH' then interval '1 month' " +
        "when :dateFilter = 'YEAR' then interval '1 year' end) " +
        "and (:durationFilter is null or case " +
        "when :durationFilter = 'SHORT' then length < 180000000000 " +
        "when :durationFilter = 'MEDIUM' then length between 180000000000 and 900000000000 " +
        "when :durationFilter = 'LONG' then length > 900000000000 end) " +
        "and coalesce(:sortOrder, '') is not null " +
        "order by case when :sortOrder = 'RELEVANCE' then strict_word_similarity(cast(:phrase as text), title || ' ' || coalesce(description, '')) end desc, " +
        "case when :sortOrder = 'DATE' then uploaded end desc, " +
        "case when :sortOrder = 'VIEWS' then views end desc")
    Page<VideoEntity> search(
        @Param("phrase") String phrase,
        @Param("channel") String channel,
        @Param("dateFilter") String dateFilter,
        @Param("durationFilter") String durationFilter,
        @Param("sortOrder") String sortOrder,
        Pageable pageable
    );
}
