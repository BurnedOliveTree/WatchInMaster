package pw.edu.watchin.server.service.video;

import lombok.Value;
import lombok.With;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
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

import java.io.InputStream;
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
        // TODO
        var streams = getStreams(pageRequest).stream()
            .map(Stream::toMyStreamWithStatsDTO)
            .collect(Collectors.toList());
        return new PageResponse<>(streams, 0, 0, streams.size());
    }

    public void viewStream(UUID id, Account account) {
        // TODO
    }

    public void deleteStream(UUID id, Account account) {
        streams.remove(getStream(id));
    }

    @Transactional
    public void setTitle(UUID id, String title, Account account) {
        var stream = getStream(id);
        streams.remove(stream);
        streams.add(stream.withTitle(title));
    }

    @Transactional
    public void setDescription(UUID id, @Nullable String description, Account account) {
        var stream = getStream(id);
        streams.remove(stream);
        streams.add(stream.withDescription(description));
    }

    @Transactional
    public void setVisibility(UUID id, VideoVisibilityType visibility, Account account) {
        var stream = getStream(id);
        streams.remove(stream);
        streams.add(stream.withVisibility(visibility));
    }

    @Transactional
    public void setThumbnail(UUID id, @Nullable InputStream thumbnailSource, Account account) {
        // TODO implement
    }

    public PageResponse<ListableStream> findStreams(PageRequest<Void> pageRequest, Account account) {
        // TODO
        var streams = getStreams(pageRequest).stream()
                .map(Stream::toListable)
                .collect(Collectors.toList());
        return new PageResponse<>(streams, 0, 0, streams.size());
    }

    Stream getStream(UUID id) {
        return streams.stream()
            .filter(stream -> stream.id.equals(id))
            .findFirst()
            .orElseThrow(EntityNotFoundException::new);
    }

    List<Stream> getStreams(PageRequest pageRequest) {
        return this.streams.stream()
            .skip(pageRequest.getPage() * pageRequest.getSize())
            .limit(pageRequest.getSize())
            .collect(Collectors.toList());
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

    @With
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
        ResourceDTO thumbnail = new ResourceDTO("", ResourceType.AVATAR); // TODO actual thumbnail

        ListableStream toListable() {
            return new ListableStream(
                id,
                title,
                description,
                channelMapperService.mapTile(author),
                Duration.ZERO,
                uploaded,
                0,
                thumbnail
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
                thumbnail,
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
                thumbnail,
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
        ResourceDTO thumbnail;
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
        ResourceDTO thumbnail;
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
