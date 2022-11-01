package pw.edu.watchin.server.service.scheduler;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pw.edu.watchin.server.service.channel.ChannelStatisticsService;
import pw.edu.watchin.server.service.resource.ResourceService;

@Service
public class SchedulerService {

    @Autowired
    private ChannelStatisticsService channelStatisticsService;

    @Autowired
    private ResourceService resourceService;

    @Scheduled(cron = "#{@schedulerProperties.statistics.generate}")
    @SchedulerLock(name = "generateChannelStatistics")
    public void generateChannelStatistics() {
        channelStatisticsService.generateStatistics();
    }

    @Scheduled(cron = "#{@schedulerProperties.resources.purgeOrphans}")
    @SchedulerLock(name = "purgeResourcesOrphans")
    public void purgeResourcesOrphans() {
        resourceService.purgeOrphans();
    }
}
