package pw.edu.watchin.server.web.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import pw.edu.watchin.server.dto.pagination.PageRequest;
import pw.edu.watchin.server.dto.pagination.PageResponse;
import pw.edu.watchin.server.dto.video.*;
import pw.edu.watchin.server.security.Account;
import pw.edu.watchin.server.security.AuthAccount;
import pw.edu.watchin.server.service.video.VideoService;

import java.util.UUID;

@RestController
@RequestMapping("/api/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/{id}")
    public VideoDTO getVideoDetails(@PathVariable UUID id, @AuthAccount @Nullable Account account) {
        return videoService.getVideoDetails(id, account);
    }

    @PostMapping("/{id}/view")
    public void viewVideo(@PathVariable UUID id, @AuthAccount @Nullable Account account) {
        videoService.viewVideo(id, account);
    }

    @PostMapping("/{id}/like")
    public VideoLikesDTO videoLikeAction(@PathVariable UUID id, @AuthAccount Account account, @RequestBody VideoLikeActionDTO action) {
        return videoService.videoLikeAction(id, account, action);
    }

    @PostMapping("/{id}/comments")
    public PageResponse<VideoCommentDTO> getVideoComments(@PathVariable UUID id, @RequestBody PageRequest<Void> pageRequest) {
        return videoService.getVideoComments(id, pageRequest);
    }

    @PostMapping("/{id}/comment")
    public void videoCommentAction(@PathVariable UUID id, @AuthAccount Account account, @RequestBody VideoCommentActionDTO action) {
        videoService.videoCommentAction(id, account, action);
    }

    @PostMapping("/{id}/favorite")
    public VideoFavoriteDTO videoFavoriteAction(@PathVariable UUID id, @AuthAccount Account account, @RequestBody VideoFavoriteActionDTO action) {
        return videoService.videoFavoriteAction(id, account, action);
    }

    @PostMapping("/{id}/watch-later")
    public VideoWatchLaterDTO videoWatchLaterAction(@PathVariable UUID id, @AuthAccount Account account, @RequestBody VideoWatchLaterActionDTO action) {
        return videoService.videoWatchLaterAction(id, account, action);
    }
}
