package pw.edu.watchin.server.service.channel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pw.edu.watchin.media.service.ImageFileService;
import pw.edu.watchin.server.domain.account.AccountEntity;
import pw.edu.watchin.server.domain.channel.ChannelEntity;
import pw.edu.watchin.server.dto.channel.ChannelEditDTO;
import pw.edu.watchin.server.dto.resource.ResourceDTO;
import pw.edu.watchin.server.exception.EntityNotFoundException;
import pw.edu.watchin.server.repository.channel.ChannelRepository;
import pw.edu.watchin.server.security.Account;
import pw.edu.watchin.server.service.resource.ResourceMapperService;
import pw.edu.watchin.server.service.resource.ResourceService;

import java.io.InputStream;
import java.util.Optional;

@Service
public class ChannelManagementService {

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private ChannelMapperService channelMapperService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ResourceMapperService resourceMapperService;

    @Autowired
    private ImageFileService imageFileService;

    @Transactional
    public void createChannel(AccountEntity account) {
        var avatarImage = imageFileService.generateAvatarFromUsername(account.getUsername());
        var avatar = resourceService.saveAvatar(avatarImage);
        var channel = new ChannelEntity();
        channel.setAccount(account);
        channel.setAvatar(avatar);
        channelRepository.save(channel);
    }

    @Transactional(readOnly = true)
    public ChannelEditDTO getChannelForEdition(Account account) {
        return channelRepository.findByAccountId(account.getId())
            .map(channelMapperService::mapEdit)
            .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public ResourceDTO saveAvatar(@Nullable InputStream avatarSource, Account account) {
        var channel = channelRepository.findById(account.getId())
            .orElseThrow(EntityNotFoundException::new);

        var avatarImage = Optional.ofNullable(avatarSource)
            .map(imageFileService::handleUploadedAvatar)
            .orElseGet(() -> imageFileService.generateAvatarFromUsername(account.getUsername()));

        var avatar = resourceService.saveAvatar(avatarImage);

        channel.setAvatar(avatar);
        return resourceMapperService.getResourceLocation(avatar);
    }

    @Transactional
    public ResourceDTO saveBackground(@Nullable InputStream backgroundSource, Account account) {
        var channel = channelRepository.findById(account.getId())
            .orElseThrow(EntityNotFoundException::new);

        var background = Optional.ofNullable(backgroundSource)
            .map(imageFileService::handleUploadedBackground)
            .map(resourceService::saveBackground)
            .orElse(null);

        channel.setBackground(background);
        return resourceMapperService.getResourceLocation(background);
    }

    @Transactional
    public String saveDescription(@Nullable String description, Account account) {
        var channel = channelRepository.findById(account.getId()).orElseThrow(EntityNotFoundException::new);
        channel.setDescription(description);
        return description;
    }
}
