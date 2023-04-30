package pw.edu.watchin.server.web.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pw.edu.watchin.server.dto.pagination.PageRequest;
import pw.edu.watchin.server.dto.pagination.PageResponse;
import pw.edu.watchin.server.dto.search.VideoTableFilterDTO;
import pw.edu.watchin.server.dto.video.VideoEditDTO;
import pw.edu.watchin.server.security.Account;
import pw.edu.watchin.server.security.AuthAccount;
import pw.edu.watchin.server.service.video.StreamService;
import pw.edu.watchin.server.service.video.VideoManagementService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/stream/manage")
public class StreamManagementController {

    @Autowired
    private VideoManagementService videoManagementService;

    @Autowired
    private StreamService streamService;

    @PostMapping
    public StreamService.MyStreamDTO createStream(@RequestBody String title, @AuthAccount Account account) {
        return streamService.generateStream(title, account);
    }

    // TODO add security
    @PostMapping("/{id}")
    public VideoEditDTO uploadStream(@PathVariable UUID id, @RequestPart MultipartFile video) throws IOException {
        return videoManagementService.fromStream(id, video);
    }

    @DeleteMapping("/{id}")
    public void deleteStream(@PathVariable UUID id, @AuthAccount Account account) {
        streamService.deleteStream(id, account);
    }

    @PostMapping("/list")
    public PageResponse<StreamService.MyStreamWithStatsDTO> getAllStreams(@RequestBody PageRequest<VideoTableFilterDTO> pageRequest, @AuthAccount Account account) {
        return streamService.getAllStreams(pageRequest, account);
    }
}
