package pw.edu.watchin.server.web.channel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import pw.edu.watchin.server.dto.channel.ChannelDTO;
import pw.edu.watchin.server.dto.channel.SubscriptionActionDTO;
import pw.edu.watchin.server.dto.channel.SubscriptionDTO;
import pw.edu.watchin.server.security.Account;
import pw.edu.watchin.server.security.AuthAccount;
import pw.edu.watchin.server.service.channel.ChannelService;

@RestController
@RequestMapping("/api/channel")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    @GetMapping("/{name}")
    public ChannelDTO getChannelDetails(@PathVariable String name) {
        return channelService.getChannelDetails(name);
    }

    @PostMapping("/{name}/subscribe")
    public SubscriptionDTO channelSubscriptionAction(@PathVariable String name, @AuthAccount Account account, @RequestBody SubscriptionActionDTO action) {
        return channelService.channelSubscriptionAction(name, account, action);
    }

    @GetMapping("/{name}/subscription")
    public SubscriptionDTO getChannelSubscriptionStatus(@PathVariable String name, @Nullable @AuthAccount Account account) {
        return channelService.getChannelSubscriptionStatus(name, account);
    }
}
