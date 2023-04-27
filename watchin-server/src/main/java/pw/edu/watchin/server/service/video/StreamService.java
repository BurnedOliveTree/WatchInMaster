package pw.edu.watchin.server.service.video;

import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pw.edu.watchin.server.domain.channel.ChannelEntity;
import pw.edu.watchin.server.domain.resource.ResourceType;
import pw.edu.watchin.server.dto.channel.ChannelTileDTO;
import pw.edu.watchin.server.dto.pagination.PageRequest;
import pw.edu.watchin.server.dto.pagination.PageResponse;
import pw.edu.watchin.server.dto.resource.ResourceDTO;
import pw.edu.watchin.server.repository.channel.ChannelRepository;
import pw.edu.watchin.server.security.Account;
import pw.edu.watchin.server.service.channel.ChannelMapperService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

// TODO refactor
@Service
public class StreamService {
    @Autowired
    ChannelRepository channelRepository;
    @Autowired
    ChannelMapperService channelMapperService;

    // TODO move to DB
    private final Set<Stream> streams = new HashSet<>();

    @Transactional
    public MyStreamDTO generateStream(Account account) {
        // TODO
        var id = UUID.randomUUID();
        var stream = new Stream(id, "TODO", "TODO",
            rtmp, createHLS(id),
            channelRepository.findByAccountId(account.getId()).get(),
            LocalDateTime.now());
        streams.add(stream);
        return stream.toMyStreamDTO();
    }

    public void viewStream(UUID id, Account account) {
        // TODO
    }

    public PageResponse<ListableStream> findStreams(PageRequest<Void> pageRequest, Account account) {
        // TODO
        return new PageResponse<>(new ArrayList<>(streams.stream().map(Stream::toListable).collect(Collectors.toList())),
                1, 1, streams.size());
    }

    Stream getStream(UUID id) {
        // TODO what if not found?
        return streams.stream().filter(stream -> stream.id.equals(id)).findFirst().get();
    }

    private String createHLS(UUID id) {
        return hls + "/" + id + ".m3u8";
    }

    private String createDASH(UUID id) {
        return dash + "/" + id + ".mpd";
    }

    // TODO move to config.yaml
    private static final String rtmp = "rtmp://192.168.0.156/live";
    private static final String hls = "http://192.168.0.156:8082/hls";
    private static final String dash = "http://192.168.0.156:8082/dash";

    @Value
    class Stream {
        UUID id;
        String title;
        String description;
        String uploadUrl;
        String watchUrl;
        ChannelEntity author;
        LocalDateTime uploaded;

        // TODO
        ListableStream toListable() {
            return new ListableStream(
                id,
                title,
                description,
                channelMapperService.mapTile(author),
                Duration.ZERO,
                uploaded,
                0,
                new ResourceDTO("", ResourceType.AVATAR)
            );
        }

        MyStreamDTO toMyStreamDTO() {
            return new MyStreamDTO(
                id,
                title,
                description,
                uploadUrl,
                watchUrl,
                channelMapperService.mapTile(author),
                uploaded
            );
        }
    }

    @Value
    public class MyStreamDTO {
        UUID id;
        String title;
        String description;
        String uploadUrl;
        String watchUrl;
        ChannelTileDTO author;
        LocalDateTime uploaded;
    }

    @Value
    public class ListableStream {
        UUID id;
        String title;
        String description;
        ChannelTileDTO channel;
        Duration length;
        LocalDateTime uploaded;
        long views;
        ResourceDTO thumbnail;
    }
}
