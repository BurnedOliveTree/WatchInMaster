package pw.edu.watchin.server.service.video;

import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pw.edu.watchin.server.domain.channel.ChannelEntity;
import pw.edu.watchin.server.domain.resource.ResourceType;
import pw.edu.watchin.server.domain.video.VideoQualityType;
import pw.edu.watchin.server.domain.video.VideoVisibilityType;
import pw.edu.watchin.server.dto.channel.ChannelTileDTO;
import pw.edu.watchin.server.dto.pagination.PageRequest;
import pw.edu.watchin.server.dto.pagination.PageResponse;
import pw.edu.watchin.server.dto.resource.ResourceDTO;
import pw.edu.watchin.server.dto.search.VideoTableFilterDTO;
import pw.edu.watchin.server.dto.video.*;
import pw.edu.watchin.server.exception.EntityNotFoundException;
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
    public MyStreamDTO generateStream(String title, Account account) {
        // TODO
        var id = UUID.randomUUID();
        var stream = new Stream(
            id,
            title,
            null,
            rtmp,
            createHLS(id),
            channelRepository.findByAccountId(account.getId())
                .orElseThrow(EntityNotFoundException::new),
            LocalDateTime.now(),
            Duration.ZERO, // TODO
            0,
            VideoVisibilityType.PUBLIC // TODO
        );
        streams.add(stream);
        return stream.toMyStreamDTO();
    }

    public FullStream getStreamDetails(UUID id, Account account) {
        return getStream(id).toWatchableStream();
    }

    public PageResponse<MyStreamWithStatsDTO> getAllStreams(PageRequest<VideoTableFilterDTO> pageRequest, Account account) {
        return new PageResponse<>(streams.stream().map(Stream::toMyStreamWithStatsDTO).collect(Collectors.toList()),
                1, 1, streams.size());
    }

    public void viewStream(UUID id, Account account) {
        // TODO
    }

    public void deleteStream(UUID id, Account account) {
        streams.remove(getStream(id));
    }

    public PageResponse<ListableStream> findStreams(PageRequest<Void> pageRequest, Account account) {
        // TODO
        return new PageResponse<>(streams.stream().map(Stream::toListable).collect(Collectors.toList()),
                1, 1, streams.size());
    }

    Stream getStream(UUID id) {
        return streams.stream()
            .filter(stream -> stream.id.equals(id))
            .findFirst()
            .orElseThrow(EntityNotFoundException::new);
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
        Duration length;
        long views;
        VideoVisibilityType visibility;

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
                uploaded,
                visibility
            );
        }

        MyStreamWithStatsDTO toMyStreamWithStatsDTO() {
            return new MyStreamWithStatsDTO(
                id,
                title,
                description,
                uploadUrl,
                watchUrl,
                channelMapperService.mapTile(author),
                length,
                uploaded,
                views,
                visibility
            );
        }

        FullStream toWatchableStream() {
            return new FullStream(
                id,
                title,
                description,
                uploaded,
                length,
                views,
                channelMapperService.mapTile(author),
                List.of(
                    new VideoResourceDTO(
                        new ResourceDTO(watchUrl, ResourceType.VIDEO),
                        new VideoQualityDTO(
                            VideoQualityType.RESOLUTION_720p,
                            VideoQualityType.RESOLUTION_720p.getFriendlyName()
                        )
                    )
                ),
                visibility,
                new VideoLikesDTO(0, 0, false, false), // TODO
                new VideoFavoriteDTO(false), // TODO
                new VideoWatchLaterDTO(false) // TODO
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
        VideoVisibilityType visibility;
    }

    @Value
    public class MyStreamWithStatsDTO {
        UUID id;
        String title;
        String description;
        String uploadUrl;
        String watchUrl;
        ChannelTileDTO author;
        Duration length;
        LocalDateTime uploaded;
        long views;
        VideoVisibilityType visibility;
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

    @Value
    public class FullStream {
        UUID id;
        String title;
        String description;
        LocalDateTime uploaded;
        Duration length;
        long views;
        ChannelTileDTO channel;
        List<VideoResourceDTO> resources;
        VideoVisibilityType visibility;
        VideoLikesDTO likes;
        VideoFavoriteDTO favorite;
        VideoWatchLaterDTO watchLater;
    }
}
