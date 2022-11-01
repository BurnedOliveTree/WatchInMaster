package pw.edu.watchin.server.repository.video;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import pw.edu.watchin.server.domain.video.VideoEntity;
import pw.edu.watchin.server.security.Account;

public interface VideoSearchRepositoryCustom {

    Page<VideoEntity> searchWithFilterEnabled(
        String phrase,
        String channel,
        String dateFilter,
        String durationFilter,
        String sortOrder,
        Pageable pageable,
        @Nullable Account account
    );
}
