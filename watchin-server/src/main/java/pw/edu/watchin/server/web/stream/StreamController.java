package pw.edu.watchin.server.web.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import pw.edu.watchin.server.security.Account;
import pw.edu.watchin.server.security.AuthAccount;
import pw.edu.watchin.server.service.video.StreamService;

import java.util.UUID;

@RestController
@RequestMapping("/api/stream")
public class StreamController {
    @Autowired
    private StreamService streamService;

    @GetMapping("/{id}")
    public StreamService.FullStream getStreamDetails(@PathVariable UUID id, @AuthAccount @Nullable Account account) {
        return streamService.getStreamDetails(id, account);
    }

    @PostMapping("/{id}/view")
    public void viewStream(@PathVariable UUID id, @AuthAccount @Nullable Account account) {
        streamService.viewStream(id, account);
    }
}
