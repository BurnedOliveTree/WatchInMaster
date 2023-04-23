package pw.edu.watchin.server.service.video;

import lombok.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pw.edu.watchin.server.dto.pagination.PageRequest;
import pw.edu.watchin.server.dto.pagination.PageResponse;
import pw.edu.watchin.server.security.Account;

import java.util.*;

// TODO refactor
@Service
public class StreamService {
    // TODO move to DB
    private final Set<Stream> streams = new HashSet<>();

    @Transactional
    public Stream generateStream(Account account) {
        // TODO
        var key = UUID.randomUUID();
        var stream = new Stream(key, url, url + "/" + key, account);
        streams.add(stream);
        return stream;
    }

    public void viewStream(UUID id, Account account) {
        // TODO
    }

    public PageResponse<Stream> findStreams(PageRequest<Void> pageRequest, Account account) {
        // TODO
        return new PageResponse<>(new ArrayList<>(streams), 1, 1, streams.size());
    }

    Stream getStream(UUID id) {
        // TODO what if not found?
        return streams.stream().filter(stream -> stream.id.equals(id)).findFirst().get();
    }

    // TODO move to config.yaml
    private static final String url = "rtmp://192.168.0.156/live";

    @Value
    public class Stream {
        UUID id;
        String uploadUrl;
        String watchUrl;
        Account author;
    }
}
