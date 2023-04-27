package pw.edu.watchin.server.web.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pw.edu.watchin.server.domain.video.VideoStatusType;
import pw.edu.watchin.server.domain.video.VideoVisibilityType;
import pw.edu.watchin.server.dto.pagination.PageRequest;
import pw.edu.watchin.server.dto.pagination.PageResponse;
import pw.edu.watchin.server.dto.resource.ResourceDTO;
import pw.edu.watchin.server.dto.search.VideoTableFilterDTO;
import pw.edu.watchin.server.dto.video.VideoEditDTO;
import pw.edu.watchin.server.dto.video.VideoTableEntryDTO;
import pw.edu.watchin.server.security.Account;
import pw.edu.watchin.server.security.AuthAccount;
import pw.edu.watchin.server.service.video.StreamService;
import pw.edu.watchin.server.service.video.VideoManagementService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/video/manage")
public class VideoManagementController {

    @Autowired
    private VideoManagementService videoManagementService;

    @Autowired
    private StreamService streamService;

    @PostMapping("/upload")
    public VideoEditDTO uploadVideo(@RequestPart MultipartFile video, @AuthAccount Account account) throws IOException {
        return videoManagementService.createVideo(account, video);
    }

    @PostMapping("/stream")
    public StreamService.MyStreamDTO createStream(@AuthAccount Account account) {
        return streamService.generateStream(account);
    }

    // TODO add security
    @PostMapping("/stream/{id}")
    public VideoEditDTO uploadStream(@PathVariable UUID id, @RequestPart MultipartFile video) throws IOException {
        return videoManagementService.fromStream(id, video);
    }

    @GetMapping("/{id}")
    public VideoEditDTO getVideoForEdition(@PathVariable UUID id, @AuthAccount Account account) {
        return videoManagementService.getVideoForEdition(id, account);
    }

    @DeleteMapping("/{id}")
    public void deleteVideo(@PathVariable UUID id, @AuthAccount Account account) {
        videoManagementService.deleteVideo(id, account);
    }

    @PostMapping("/{id}/title")
    public String saveTitle(@PathVariable UUID id, @RequestBody String title, @AuthAccount Account account) {
        return videoManagementService.saveTitle(id, title, account);
    }

    @PostMapping("/{id}/description")
    public String saveDescription(@PathVariable UUID id, @RequestBody String description, @AuthAccount Account account) {
        return videoManagementService.saveDescription(id, description, account);
    }

    @DeleteMapping("/{id}/description")
    public String deleteDescription(@PathVariable UUID id, @AuthAccount Account account) {
        return videoManagementService.saveDescription(id, null, account);
    }

    @PostMapping(value = "/{id}/visibility")
    public String saveVisibility(@PathVariable UUID id, @RequestBody String visibility, @AuthAccount Account account) {
        return videoManagementService.saveVisibility(id, VideoVisibilityType.valueOf(visibility), account).name();
    }

    @PostMapping("/{id}/thumbnail")
    public ResourceDTO saveThumbnail(@PathVariable UUID id, @RequestPart MultipartFile thumbnail, @AuthAccount Account account) throws IOException {
        return videoManagementService.saveThumbnail(id, thumbnail.getInputStream(), account);
    }

    @DeleteMapping("/{id}/thumbnail")
    public ResourceDTO deleteThumbnail(@PathVariable UUID id, @AuthAccount Account account) {
        return videoManagementService.saveThumbnail(id, null, account);
    }

    @PostMapping("/list")
    public PageResponse<VideoTableEntryDTO> getAllVideos(@RequestBody PageRequest<VideoTableFilterDTO> pageRequest, @AuthAccount Account account) {
        return videoManagementService.getAllVideos(pageRequest, account);
    }

    @GetMapping("/{id}/status")
    public VideoStatusType getVideoStatus(@PathVariable UUID id, @AuthAccount Account account) {
        return videoManagementService.getVideoStatus(id, account);
    }
}
