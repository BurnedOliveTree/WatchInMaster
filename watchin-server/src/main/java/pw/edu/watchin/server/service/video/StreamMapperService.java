package pw.edu.watchin.server.service.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.edu.watchin.server.domain.resource.ResourceType;
import pw.edu.watchin.server.domain.video.StreamEntity;
import pw.edu.watchin.server.domain.video.VideoQualityType;
import pw.edu.watchin.server.dto.resource.ResourceDTO;
import pw.edu.watchin.server.dto.stream.EditableStreamDTO;
import pw.edu.watchin.server.dto.stream.FullStreamDTO;
import pw.edu.watchin.server.dto.stream.ListableStreamDTO;
import pw.edu.watchin.server.dto.stream.WatchableStreamDTO;
import pw.edu.watchin.server.dto.video.*;
import pw.edu.watchin.server.service.channel.ChannelMapperService;
import pw.edu.watchin.server.service.resource.ResourceMapperService;

import java.util.List;

@Service
public class StreamMapperService {
    @Autowired
    private ChannelMapperService channelMapperService;
    @Autowired
    private ResourceMapperService resourceMapperService;

    WatchableStreamDTO toWatchableStream(StreamEntity stream) {
        return new WatchableStreamDTO(
            stream.getId(),
            stream.getTitle(),
            stream.getDescription(),
            stream.getUploaded(),
            stream.getLength(),
            stream.getViews(),
            channelMapperService.mapTile(stream.getChannel()),
            List.of( // TODO
                new VideoResourceDTO(
                    new ResourceDTO(stream.getWatchUrl(), ResourceType.VIDEO),
                    new VideoQualityDTO(
                        VideoQualityType.RESOLUTION_720p,
                        VideoQualityType.RESOLUTION_720p.getFriendlyName()
                    )
                )
            ),
            stream.getVisibility(),
            new VideoLikesDTO(0, 0, false, false), // TODO
            new VideoFavoriteDTO(false), // TODO
            new VideoWatchLaterDTO(false) // TODO
        );
    }

    ListableStreamDTO toListable(StreamEntity stream) {
        return new ListableStreamDTO(
            stream.getId(),
            stream.getTitle(),
            stream.getDescription(),
            channelMapperService.mapTile(stream.getChannel()),
            stream.getLength(),
            stream.getUploaded(),
            stream.getViews(),
            resourceMapperService.getResourceLocation(stream.getThumbnail())
        );
    }

    EditableStreamDTO toEditable(StreamEntity stream) {
        return new EditableStreamDTO(
            stream.getId(),
            stream.getTitle(),
            stream.getDescription(),
            stream.getUploadUrl(),
            stream.getWatchUrl(),
            channelMapperService.mapTile(stream.getChannel()),
            resourceMapperService.getResourceLocation(stream.getThumbnail()),
            stream.getUploaded(),
            stream.getVisibility()
        );
    }


    FullStreamDTO toFull(StreamEntity stream) {
        return new FullStreamDTO(
            stream.getId(),
            stream.getTitle(),
            stream.getDescription(),
            stream.getUploadUrl(),
            stream.getWatchUrl(),
            channelMapperService.mapTile(stream.getChannel()),
            resourceMapperService.getResourceLocation(stream.getThumbnail()),
            stream.getLength(),
            stream.getUploaded(),
            stream.getViews(),
            stream.getVisibility()
        );
    }
}
