package pw.edu.watchin.server.domain.video;

import lombok.Getter;
import lombok.Setter;
import pw.edu.watchin.server.domain.channel.ChannelEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "video_favorite", uniqueConstraints = @UniqueConstraint(columnNames = {"channel_id", "video_id"}))
public class VideoFavoriteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private VideoEntity video;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    private ChannelEntity channel;
}
