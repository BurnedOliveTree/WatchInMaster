package pw.edu.watchin.server.domain.video;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import pw.edu.watchin.server.domain.channel.ChannelEntity;
import pw.edu.watchin.server.domain.resource.ResourceEntity;
import pw.edu.watchin.server.security.Owner;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "video")
@Owner(target = "channel.account")
@FilterDef(name = "requestingChannel", parameters = @ParamDef(name = "channelId", type = "java.lang.Integer"), defaultCondition = "channel_id = :channelId")
public class VideoEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "length", nullable = false)
    private Duration length;

    @CreationTimestamp
    @Column(name = "uploaded", nullable = false)
    private LocalDateTime uploaded;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false)
    private VideoVisibilityType visibility;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private VideoStatusType status;

    @Column(name = "views", nullable = false)
    private long views;

    @OneToOne
    @JoinColumn(name = "thumbnail_resource_id")
    private ResourceEntity thumbnail;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "frame_resource_id")
    private ResourceEntity frame;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private ChannelEntity channel;

    @OrderBy("ordinal")
    @OneToMany(mappedBy = "video", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<VideoResourceEntity> resources;

    @OneToMany(mappedBy = "video", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<VideoCommentEntity> comments;

    @OneToMany(mappedBy = "video", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<VideoLikeEntity> likes;

    @OneToMany(mappedBy = "video", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @Filter(name = "requestingChannel")
    private List<VideoFavoriteEntity> favorites;

    @OneToMany(mappedBy = "video", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @Filter(name = "requestingChannel")
    private List<VideoWatchLaterEntity> watchLaterList;
}
