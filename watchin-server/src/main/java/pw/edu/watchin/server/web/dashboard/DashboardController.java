package pw.edu.watchin.server.web.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pw.edu.watchin.server.dto.channel.StatisticsDTO;
import pw.edu.watchin.server.security.Account;
import pw.edu.watchin.server.security.AuthAccount;
import pw.edu.watchin.server.service.channel.ChannelStatisticsService;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private ChannelStatisticsService channelStatisticsService;

    @GetMapping("/statistics")
    public List<StatisticsDTO> getStatisticsForChannel(@AuthAccount Account account) {
        return channelStatisticsService.getStatisticsForChannel(account);
    }
}
