package pw.edu.watchin.server.repository.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import pw.edu.watchin.server.domain.video.VideoEntity;
import pw.edu.watchin.server.security.Account;

public class VideoSearchRepositoryCustomImpl extends BaseVideoRepositoryWithFilter implements VideoSearchRepositoryCustom {

    @Lazy
    @Autowired
    private VideoSearchRepository videoSearchRepository;

    public Page<VideoEntity> searchWithFilterEnabled(
        String phrase,
        String channel,
        String dateFilter,
        String durationFilter,
        String sortOrder,
        Pageable pageable,
        @Nullable Account account
    ) {
        enableFilter(account);
        return videoSearchRepository.search(phrase, channel, dateFilter, durationFilter, sortOrder, pageable);
    }
}
