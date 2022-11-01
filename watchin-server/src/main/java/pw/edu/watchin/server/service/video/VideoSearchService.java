package pw.edu.watchin.server.service.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pw.edu.watchin.server.dto.pagination.PageRequest;
import pw.edu.watchin.server.dto.pagination.PageResponse;
import pw.edu.watchin.server.dto.search.VideoAutocompleteSearchResultDTO;
import pw.edu.watchin.server.dto.search.VideoSearchFilterDTO;
import pw.edu.watchin.server.dto.video.VideoTileDTO;
import pw.edu.watchin.server.repository.video.VideoSearchRepository;
import pw.edu.watchin.server.security.Account;

import java.util.Optional;

@Service
public class VideoSearchService {

    @Autowired
    private VideoSearchRepository videoSearchRepository;

    @Autowired
    private VideoMapperService videoMapperService;

    public VideoAutocompleteSearchResultDTO getAutocompleteOptions(PageRequest<VideoSearchFilterDTO> pageRequest) {
        var options = videoSearchRepository.getAutocompleteOptions(
            pageRequest.getFilter().getPhrase(),
            pageRequest.toPageable()
        );
        return new VideoAutocompleteSearchResultDTO(options);
    }

    @Transactional(readOnly = true)
    public PageResponse<VideoTileDTO> search(PageRequest<VideoSearchFilterDTO> pageRequest, @Nullable Account account) {
        var page = videoSearchRepository.searchWithFilterEnabled(
            pageRequest.getFilter().getPhrase(),
            pageRequest.getFilter().getChannel(),
            Optional.ofNullable(pageRequest.getFilter().getDate()).map(Enum::name).orElse(null),
            Optional.ofNullable(pageRequest.getFilter().getDuration()).map(Enum::name).orElse(null),
            Optional.ofNullable(pageRequest.getFilter().getSort()).map(Enum::name).orElse(null),
            pageRequest.toPageable(),
            account
        ).map(videoMapperService::mapTile);
        return new PageResponse<>(page.getContent(), pageRequest.getPage(), page.getTotalPages(), page.getTotalElements());
    }
}
