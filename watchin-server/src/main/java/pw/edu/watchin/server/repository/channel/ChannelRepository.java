package pw.edu.watchin.server.repository.channel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pw.edu.watchin.server.domain.channel.ChannelEntity;

import java.util.Optional;

public interface ChannelRepository extends JpaRepository<ChannelEntity, Integer> {

    Optional<ChannelEntity> findByAccountUsername(String username);

    Optional<ChannelEntity> findByAccountId(Integer id);

    Page<ChannelEntity> findChannelBySubscribedBySubscriberAccountId(Integer accountId, Pageable pageable);
}
