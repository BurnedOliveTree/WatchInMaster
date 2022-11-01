package pw.edu.watchin.server.repository.channel;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.edu.watchin.server.domain.channel.ChannelEntity;
import pw.edu.watchin.server.domain.channel.SubscriptionEntity;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Integer> {

    Optional<SubscriptionEntity> findByChannelAndSubscriberAccountId(ChannelEntity channel, Integer subscriberAccountId);

    void deleteByChannelAndSubscriberAccountId(ChannelEntity channel, Integer subscriberAccountId);
}
