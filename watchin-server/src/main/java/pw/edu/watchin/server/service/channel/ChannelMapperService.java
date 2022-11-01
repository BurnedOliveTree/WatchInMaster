package pw.edu.watchin.server.service.channel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import pw.edu.watchin.server.domain.channel.ChannelEntity;
import pw.edu.watchin.server.domain.channel.StatisticsEntity;
import pw.edu.watchin.server.domain.channel.SubscriptionEntity;
import pw.edu.watchin.server.dto.channel.*;
import pw.edu.watchin.server.security.Account;
import pw.edu.watchin.server.service.resource.ResourceMapperService;

import java.util.Optional;

@Service
public class ChannelMapperService {

    @Autowired
    private ResourceMapperService resourceMapperService;

    public ChannelDTO mapDetails(ChannelEntity channel) {
        return new ChannelDTO(
            channel.getAccount().getUsername(),
            channel.getCreationDate(),
            channel.getDescription(),
            resourceMapperService.getResourceLocation(channel.getAvatar()),
            resourceMapperService.getResourceLocation(channel.getBackground()),
            channel.getPublicVideos().size(),
            channel.getTotalViews(),
            channel.getSubscribedBy().size()
        );
    }

    public ChannelTileDTO mapTile(ChannelEntity channel) {
        return new ChannelTileDTO(
            channel.getAccount().getUsername(),
            resourceMapperService.getResourceLocation(channel.getAvatar()),
            channel.getSubscribedBy().size()
        );
    }

    public ChannelEditDTO mapEdit(ChannelEntity channel) {
        return new ChannelEditDTO(
            channel.getDescription(),
            resourceMapperService.getResourceLocation(channel.getAvatar()),
            resourceMapperService.getResourceLocation(channel.getBackground())
        );
    }

    public SubscriptionDTO mapSubscription(ChannelEntity channel, @Nullable SubscriptionEntity subscription, @Nullable Account account) {
        return new SubscriptionDTO(
            subscription != null,
            Optional.ofNullable(account).map(subscriber -> !channel.getAccountId().equals(subscriber.getId())).orElse(true)
        );
    }

    public StatisticsDTO mapStatistics(StatisticsEntity statistics) {
        return new StatisticsDTO(
            statistics.getDate(),
            statistics.getSubscribers(),
            statistics.getVideos(),
            statistics.getViews(),
            statistics.getComments()
        );
    }
}
