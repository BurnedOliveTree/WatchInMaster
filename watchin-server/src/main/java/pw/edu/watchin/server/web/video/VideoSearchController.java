package pw.edu.watchin.server.web.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import pw.edu.watchin.server.dto.pagination.PageRequest;
import pw.edu.watchin.server.dto.pagination.PageResponse;
import pw.edu.watchin.server.dto.search.VideoAutocompleteSearchResultDTO;
import pw.edu.watchin.server.dto.search.VideoSearchFilterDTO;
import pw.edu.watchin.server.dto.video.VideoTileDTO;
import pw.edu.watchin.server.security.Account;
import pw.edu.watchin.server.security.AuthAccount;
import pw.edu.watchin.server.service.video.VideoSearchService;

@RestController
@RequestMapping("/api/videos/search")
public class VideoSearchController {

    @Autowired
    private VideoSearchService videoSearchService;

    @PostMapping("/autocomplete")
    public VideoAutocompleteSearchResultDTO getAutocompleteOptions(@RequestBody PageRequest<VideoSearchFilterDTO> pageRequest) {
        return videoSearchService.getAutocompleteOptions(pageRequest);
    }

    @PostMapping("/filter")
    public PageResponse<VideoTileDTO> search(@RequestBody PageRequest<VideoSearchFilterDTO> pageRequest, @AuthAccount @Nullable Account account) {
        return videoSearchService.search(pageRequest, account);
    }
}
