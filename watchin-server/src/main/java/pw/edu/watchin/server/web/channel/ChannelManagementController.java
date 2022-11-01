package pw.edu.watchin.server.web.channel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pw.edu.watchin.server.dto.channel.ChannelEditDTO;
import pw.edu.watchin.server.dto.resource.ResourceDTO;
import pw.edu.watchin.server.security.Account;
import pw.edu.watchin.server.security.AuthAccount;
import pw.edu.watchin.server.service.channel.ChannelManagementService;

import java.io.IOException;

@RestController
@RequestMapping("/api/channel/manage")
public class ChannelManagementController {

    @Autowired
    private ChannelManagementService channelManagementService;

    @GetMapping("/edit")
    public ChannelEditDTO getChannelForEdition(@AuthAccount Account account) {
        return channelManagementService.getChannelForEdition(account);
    }

    @PostMapping("/avatar")
    public ResourceDTO saveAvatar(@RequestPart MultipartFile avatar, @AuthAccount Account account) throws IOException {
        return channelManagementService.saveAvatar(avatar.getInputStream(), account);
    }

    @DeleteMapping("/avatar")
    public ResourceDTO deleteAvatar(@AuthAccount Account account) {
        return channelManagementService.saveAvatar(null, account);
    }

    @PostMapping("/background")
    public ResourceDTO saveBackground(@RequestPart MultipartFile background, @AuthAccount Account account) throws IOException {
        return channelManagementService.saveBackground(background.getInputStream(), account);
    }

    @DeleteMapping("/background")
    public ResourceDTO deleteBackground(@AuthAccount Account account) {
        return channelManagementService.saveBackground(null, account);
    }

    @PostMapping("/description")
    public String saveDescription(@RequestBody String description, @AuthAccount Account account) {
        return channelManagementService.saveDescription(description, account);
    }

    @DeleteMapping("/description")
    public String saveDescription(@AuthAccount Account account) {
        return channelManagementService.saveDescription(null, account);
    }
}
