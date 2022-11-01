package pw.edu.watchin.server.service.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pw.edu.watchin.server.dto.pagination.PageRequest;
import pw.edu.watchin.server.dto.pagination.PageResponse;
import pw.edu.watchin.server.dto.video.VideoTileDTO;
import pw.edu.watchin.server.repository.video.VideoRepository;
import pw.edu.watchin.server.security.Account;

import java.util.UUID;

@Service
public class VideoListService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoMapperService videoMapperService;

    @Transactional(readOnly = true)
    public PageResponse<VideoTileDTO> findMostPopular(PageRequest<Void> pageRequest, @Nullable Account account) {
        return findPublicVideos(pageRequest, account, Sort.by(Sort.Direction.DESC, "views"));
    }

    @Transactional(readOnly = true)
    public PageResponse<VideoTileDTO> findNewest(PageRequest<Void> pageRequest, @Nullable Account account) {
        return findPublicVideos(pageRequest, account, Sort.by(Sort.Direction.DESC, "uploaded"));
    }

    @Transactional(readOnly = true)
    public PageResponse<VideoTileDTO> findFromSubscribedChannels(PageRequest<Void> pageRequest, Account account) {
        var page = videoRepository.findFromSubscribedChannelsWithFilterEnabled(
            pageRequest.toPageable(),
            account
        ).map(videoMapperService::mapTile);
        return new PageResponse<>(page.getContent(), pageRequest.getPage(), page.getTotalPages(), page.getTotalElements());
    }

    @Transactional(readOnly = true)
    public PageResponse<VideoTileDTO> findFavorite(PageRequest<Void> pageRequest, Account account) {
        var page = videoRepository.findFavoriteVideosWithFilterEnabled(
            pageRequest.toPageable(),
            account
        ).map(videoMapperService::mapTile);
        return new PageResponse<>(page.getContent(), pageRequest.getPage(), page.getTotalPages(), page.getTotalElements());
    }

    @Transactional(readOnly = true)
    public PageResponse<VideoTileDTO> findWatchLater(PageRequest<Void> pageRequest, Account account) {
        var page = videoRepository.findWatchLaterVideosWithFilterEnabled(
            pageRequest.toPageable(),
            account
        ).map(videoMapperService::mapTile);
        return new PageResponse<>(page.getContent(), pageRequest.getPage(), page.getTotalPages(), page.getTotalElements());
    }

    @Transactional(readOnly = true)
    public PageResponse<VideoTileDTO> findRelated(UUID id, PageRequest<Void> pageRequest, @Nullable Account account) {
        var page = videoRepository.findRelatedVideosWithFilterEnabled(
            id,
            pageRequest.toPageable(),
            account
        ).map(videoMapperService::mapTile);
        return new PageResponse<>(page.getContent(), pageRequest.getPage(), page.getTotalPages(), page.getTotalElements());
    }

    private PageResponse<VideoTileDTO> findPublicVideos(PageRequest<Void> pageRequest, @Nullable Account account, Sort sort) {
        var page = videoRepository.findPublicVideosWithFilterEnabled(
            pageRequest.toPageable(sort),
            account
        ).map(videoMapperService::mapTile);
        return new PageResponse<>(page.getContent(), pageRequest.getPage(), page.getTotalPages(), page.getTotalElements());
    }
}
