package pw.edu.watchin.server.domain.channel;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "statistics")
public class StatisticsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @CreationTimestamp
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "subscribers", nullable = false)
    private long subscribers;

    @Column(name = "videos", nullable = false)
    private long videos;

    @Column(name = "views", nullable = false)
    private long views;

    @Column(name = "comments", nullable = false)
    private long comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    private ChannelEntity channel;
}
