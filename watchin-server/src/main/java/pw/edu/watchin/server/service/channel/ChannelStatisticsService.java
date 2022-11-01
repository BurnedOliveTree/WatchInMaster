package pw.edu.watchin.server.service.channel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pw.edu.watchin.server.dto.channel.StatisticsDTO;
import pw.edu.watchin.server.repository.channel.StatisticsRepository;
import pw.edu.watchin.server.security.Account;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChannelStatisticsService {

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private ChannelMapperService channelMapperService;

    @Transactional
    public void generateStatistics() {
        statisticsRepository.updateStatisticsForChannels();
    }

    public List<StatisticsDTO> getStatisticsForChannel(Account account) {
        return statisticsRepository.findAllByChannelAccountIdOrderByDateAsc(account.getId())
            .stream()
            .map(channelMapperService::mapStatistics)
            .collect(Collectors.toUnmodifiableList());
    }
}
