package pw.edu.watchin.server.web.channel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pw.edu.watchin.server.dto.channel.ChannelDTO;
import pw.edu.watchin.server.dto.pagination.PageRequest;
import pw.edu.watchin.server.dto.pagination.PageResponse;
import pw.edu.watchin.server.security.Account;
import pw.edu.watchin.server.security.AuthAccount;
import pw.edu.watchin.server.service.channel.ChannelListService;

@RestController
@RequestMapping("/api/channels")
public class ChannelListController {

    @Autowired
    private ChannelListService channelListService;

    @PostMapping("/subscribed")
    public PageResponse<ChannelDTO> getSubscribedChannels(@RequestBody PageRequest<Void> pageRequest, @AuthAccount Account account) {
        return channelListService.getSubscribedChannels(pageRequest, account);
    }
}
