package pw.edu.watchin.server.web.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import pw.edu.watchin.server.dto.pagination.PageRequest;
import pw.edu.watchin.server.dto.pagination.PageResponse;
import pw.edu.watchin.server.security.Account;
import pw.edu.watchin.server.security.AuthAccount;
import pw.edu.watchin.server.service.video.StreamService;


@RestController
@RequestMapping("/api/streams")
public class StreamListController {
    @Autowired
    private StreamService streamService;

    @PostMapping("/list")
    public PageResponse<StreamService.ListableStream> findStreams(@RequestBody PageRequest<Void> pageRequest, @AuthAccount @Nullable Account account) {
        return streamService.findStreams(pageRequest, account);
    }
}
