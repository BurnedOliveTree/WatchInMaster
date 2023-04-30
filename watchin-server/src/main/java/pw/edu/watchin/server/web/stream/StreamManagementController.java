package pw.edu.watchin.server.web.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pw.edu.watchin.server.domain.video.VideoVisibilityType;
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

    @PostMapping("/{id}/title")
    public void setTitle(@PathVariable UUID id, @RequestBody String title, @AuthAccount Account account) {
        streamService.setTitle(id, title, account);
    }

    @PostMapping("/{id}/description")
    public void setDescription(@PathVariable UUID id, @RequestBody String description, @AuthAccount Account account) {
        streamService.setDescription(id, description, account);
    }

    @DeleteMapping("/{id}/description")
    public void deleteDescription(@PathVariable UUID id, @AuthAccount Account account) {
        streamService.setDescription(id, null, account);
    }

    @PostMapping(value = "/{id}/visibility")
    public void setVisibility(@PathVariable UUID id, @RequestBody String visibility, @AuthAccount Account account) {
        streamService.setVisibility(id, VideoVisibilityType.valueOf(visibility), account);
    }

    @PostMapping("/{id}/thumbnail")
    public void setThumbnail(@PathVariable UUID id, @RequestPart MultipartFile thumbnail, @AuthAccount Account account) throws IOException {
        streamService.setThumbnail(id, thumbnail.getInputStream(), account);
    }

    @DeleteMapping("/{id}/thumbnail")
    public void deleteThumbnail(@PathVariable UUID id, @AuthAccount Account account) {
        streamService.setThumbnail(id, null, account);
    }

    @PostMapping("/list")
    public PageResponse<StreamService.MyStreamWithStatsDTO> getAllStreams(@RequestBody PageRequest<VideoTableFilterDTO> pageRequest, @AuthAccount Account account) {
        return streamService.getAllStreams(pageRequest, account);
    }
}
