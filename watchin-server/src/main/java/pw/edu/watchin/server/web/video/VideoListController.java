package pw.edu.watchin.server.web.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import pw.edu.watchin.server.dto.pagination.PageRequest;
import pw.edu.watchin.server.dto.pagination.PageResponse;
import pw.edu.watchin.server.dto.video.VideoTileDTO;
import pw.edu.watchin.server.security.Account;
import pw.edu.watchin.server.security.AuthAccount;
import pw.edu.watchin.server.service.video.StreamService;
import pw.edu.watchin.server.service.video.VideoListService;

import java.util.UUID;

@RestController
@RequestMapping("/api/videos")
public class VideoListController {

    @Autowired
    private VideoListService videoSectionService;

    @Autowired
    private StreamService streamService;

    @PostMapping("/popular")
    public PageResponse<VideoTileDTO> findMostPopular(@RequestBody PageRequest<Void> pageRequest, @AuthAccount @Nullable Account account) {
        return videoSectionService.findMostPopular(pageRequest, account);
    }

    @PostMapping("/newest")
    public PageResponse<VideoTileDTO> findNewest(@RequestBody PageRequest<Void> pageRequest, @AuthAccount @Nullable Account account) {
        return videoSectionService.findNewest(pageRequest, account);
    }

    @PostMapping("/{id}/related")
    public PageResponse<VideoTileDTO> findRelated(@PathVariable UUID id, @RequestBody PageRequest<Void> pageRequest, @AuthAccount @Nullable Account account) {
        return videoSectionService.findRelated(id, pageRequest, account);
    }

    @PostMapping("/subscribed")
    public PageResponse<VideoTileDTO> findFromSubscribedChannels(@RequestBody PageRequest<Void> pageRequest, @AuthAccount Account account) {
        return videoSectionService.findFromSubscribedChannels(pageRequest, account);
    }

    @PostMapping("/favorite")
    public PageResponse<VideoTileDTO> findFavorite(@RequestBody PageRequest<Void> pageRequest, @AuthAccount Account account) {
        return videoSectionService.findFavorite(pageRequest, account);
    }

    @PostMapping("/watch-later")
    public PageResponse<VideoTileDTO> findWatchLater(@RequestBody PageRequest<Void> pageRequest, @AuthAccount Account account) {
        return videoSectionService.findWatchLater(pageRequest, account);
    }

    @PostMapping("/streams")
    public PageResponse<StreamService.ListableStream> findStreams(@RequestBody PageRequest<Void> pageRequest, @AuthAccount @Nullable Account account) {
        return streamService.findStreams(pageRequest, account);
    }
}
