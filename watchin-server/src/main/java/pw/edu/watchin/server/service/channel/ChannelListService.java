package pw.edu.watchin.server.service.channel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pw.edu.watchin.server.dto.channel.ChannelDTO;
import pw.edu.watchin.server.dto.pagination.PageRequest;
import pw.edu.watchin.server.dto.pagination.PageResponse;
import pw.edu.watchin.server.repository.channel.ChannelRepository;
import pw.edu.watchin.server.security.Account;

@Service
public class ChannelListService {

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private ChannelMapperService channelMapperService;

    @Transactional(readOnly = true)
    public PageResponse<ChannelDTO> getSubscribedChannels(PageRequest<Void> pageRequest, Account account) {
        var page = channelRepository.findChannelBySubscribedBySubscriberAccountId(
            account.getId(),
            pageRequest.toPageable()
        ).map(channelMapperService::mapDetails);
        return new PageResponse<>(page.getContent(), pageRequest.getPage(), page.getTotalPages(), page.getTotalElements());
    }
}
