package pw.edu.watchin.server.service.channel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pw.edu.watchin.server.domain.channel.SubscriptionEntity;
import pw.edu.watchin.server.dto.channel.ChannelDTO;
import pw.edu.watchin.server.dto.channel.SubscriptionActionDTO;
import pw.edu.watchin.server.dto.channel.SubscriptionDTO;
import pw.edu.watchin.server.exception.EntityNotFoundException;
import pw.edu.watchin.server.exception.ForbiddenException;
import pw.edu.watchin.server.repository.channel.ChannelRepository;
import pw.edu.watchin.server.repository.channel.SubscriptionRepository;
import pw.edu.watchin.server.security.Account;

import java.util.Optional;

@Service
public class ChannelService {

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private ChannelMapperService channelMapperService;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Transactional(readOnly = true)
    public ChannelDTO getChannelDetails(String name) {
        return channelRepository.findByAccountUsername(name)
            .map(channelMapperService::mapDetails)
            .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public SubscriptionDTO channelSubscriptionAction(String name, Account account, SubscriptionActionDTO action) {
        var channel = channelRepository.findByAccountUsername(name)
            .orElseThrow(EntityNotFoundException::new);

        if (channel.getAccountId().equals(account.getId())) {
            throw new ForbiddenException();
        }

        if (action.isRemoval()) {
            subscriptionRepository.deleteByChannelAndSubscriberAccountId(channel, account.getId());
            return channelMapperService.mapSubscription(channel, null, account);
        }

        var subscription = new SubscriptionEntity();
        subscription.setChannel(channel);
        subscription.setSubscriber(channelRepository.getOne(account.getId()));
        subscriptionRepository.save(subscription);

        return channelMapperService.mapSubscription(channel, subscription, account);
    }

    @Transactional(readOnly = true)
    public SubscriptionDTO getChannelSubscriptionStatus(String name, @Nullable Account account) {
        var channel = channelRepository.findByAccountUsername(name)
            .orElseThrow(EntityNotFoundException::new);

        var subscription = Optional.ofNullable(account)
            .map(Account::getId)
            .flatMap(subscriberId -> subscriptionRepository.findByChannelAndSubscriberAccountId(channel, subscriberId))
            .orElse(null);

        return channelMapperService.mapSubscription(channel, subscription, account);
    }
}
