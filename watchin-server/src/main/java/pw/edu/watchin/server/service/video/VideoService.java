package pw.edu.watchin.server.service.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pw.edu.watchin.server.domain.video.*;
import pw.edu.watchin.server.dto.pagination.PageRequest;
import pw.edu.watchin.server.dto.pagination.PageResponse;
import pw.edu.watchin.server.dto.video.*;
import pw.edu.watchin.server.exception.EntityNotFoundException;
import pw.edu.watchin.server.repository.channel.ChannelRepository;
import pw.edu.watchin.server.repository.video.*;
import pw.edu.watchin.server.security.Account;
import pw.edu.watchin.server.service.security.SecurityService;

import java.util.Optional;
import java.util.UUID;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoLikeRepository videoLikeRepository;

    @Autowired
    private VideoMapperService videoMapperService;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private VideoCommentRepository videoCommentRepository;

    @Autowired
    private VideoFavoriteRepository videoFavoriteRepository;

    @Autowired
    private VideoWatchLaterRepository videoWatchLaterRepository;

    @Autowired
    private SecurityService securityService;

    @Transactional(readOnly = true)
    public VideoDTO getVideoDetails(UUID id, @Nullable Account account) {
        var video = videoRepository.findProcessedByIdWithFilterEnabled(id, account)
            .orElseThrow(EntityNotFoundException::new);

        if (video.getVisibility() == VideoVisibilityType.PRIVATE) {
            securityService.ensureOwnership(video, account);
        }

        return videoMapperService.mapDetails(video, getVideoLikes(id, account));
    }

    private VideoLikesDTO getVideoLikes(UUID id, @Nullable Account account) {
        return videoLikeRepository.getLikeStatisticsForVideoId(id, Optional.ofNullable(account).map(Account::getId).orElse(null));
    }

    @Transactional
    public void viewVideo(UUID id, @Nullable Account account) {
        videoRepository.updateViewCounter(id);
        Optional.ofNullable(account)
            .map(Account::getId)
            .ifPresent(accountId -> videoWatchLaterRepository.deleteByVideoIdAndChannelAccountId(id, accountId));
    }

    @Transactional
    public VideoLikesDTO videoLikeAction(UUID id, Account account, VideoLikeActionDTO action) {
        if (action.isRemoval()) {
            videoLikeRepository.deleteByVideoIdAndChannelAccountIdAndLiked(id, account.getId(), action.isLike());
        } else {
            var like = videoLikeRepository.findByVideoIdAndChannelAccountId(id, account.getId()).orElseGet(() -> {
                var newEntity = new VideoLikeEntity();
                newEntity.setVideo(videoRepository.getOne(id));
                newEntity.setChannel(channelRepository.getOne(account.getId()));
                return newEntity;
            });
            like.setLiked(action.isLike());
            videoLikeRepository.save(like);
        }
        return getVideoLikes(id, account);
    }

    @Transactional(readOnly = true)
    public PageResponse<VideoCommentDTO> getVideoComments(UUID id, PageRequest<Void> pageRequest) {
        var page = videoCommentRepository.findByVideoIdOrderByCreationDateDesc(id, pageRequest.toPageable())
            .map(videoMapperService::mapComment);
        return new PageResponse<>(page.getContent(), pageRequest.getPage(), page.getTotalPages(), page.getTotalElements());
    }

    @Transactional
    public void videoCommentAction(UUID id, Account account, VideoCommentActionDTO action) {
        var comment = new VideoCommentEntity();
        comment.setVideo(videoRepository.getOne(id));
        comment.setChannel(channelRepository.getOne(account.getId()));
        comment.setContent(action.getContent());
        videoCommentRepository.save(comment);
    }

    @Transactional
    public VideoFavoriteDTO videoFavoriteAction(UUID id, Account account, VideoFavoriteActionDTO action) {
        if (action.isRemoval()) {
            videoFavoriteRepository.deleteByVideoIdAndChannelAccountId(id, account.getId());
            return new VideoFavoriteDTO(false);
        }

        var favorite = new VideoFavoriteEntity();
        favorite.setVideo(videoRepository.getOne(id));
        favorite.setChannel(channelRepository.getOne(account.getId()));
        videoFavoriteRepository.save(favorite);
        return new VideoFavoriteDTO(true);
    }

    @Transactional
    public VideoWatchLaterDTO videoWatchLaterAction(UUID id, Account account, VideoWatchLaterActionDTO action) {
        if (action.isRemoval()) {
            videoWatchLaterRepository.deleteByVideoIdAndChannelAccountId(id, account.getId());
            return new VideoWatchLaterDTO(false);
        }

        var watchLater = new VideoWatchLaterEntity();
        watchLater.setVideo(videoRepository.getOne(id));
        watchLater.setChannel(channelRepository.getOne(account.getId()));
        videoWatchLaterRepository.save(watchLater);
        return new VideoWatchLaterDTO(true);
    }
}
