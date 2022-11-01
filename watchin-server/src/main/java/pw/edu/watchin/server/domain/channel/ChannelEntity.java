package pw.edu.watchin.server.domain.channel;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import pw.edu.watchin.server.domain.account.AccountEntity;
import pw.edu.watchin.server.domain.resource.ResourceEntity;
import pw.edu.watchin.server.domain.video.VideoEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "channel")
public class ChannelEntity {

    @Id
    @Column(name = "account_id")
    private Integer accountId;

    @OneToOne
    @MapsId
    private AccountEntity account;

    @CreationTimestamp
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "avatar_resource_id")
    private ResourceEntity avatar;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "background_resource_id")
    private ResourceEntity background;

    @Formula("(select coalesce(sum(video.views), 0) from video where video.channel_id = account_id)")
    @Basic(fetch = FetchType.LAZY)
    private long totalViews;

    @OneToMany(mappedBy = "channel")
    private List<VideoEntity> videos;

    @LazyCollection(LazyCollectionOption.EXTRA)
    @OneToMany(mappedBy = "channel")
    @Where(clause = "visibility = 'PUBLIC' and status IN ('PARTIALLY_READY', 'READY')")
    private List<VideoEntity> publicVideos;

    @LazyCollection(LazyCollectionOption.EXTRA)
    @OneToMany(mappedBy = "channel")
    private List<SubscriptionEntity> subscribedBy;

    @OneToMany(mappedBy = "subscriber")
    private List<SubscriptionEntity> subscribedTo;
}
