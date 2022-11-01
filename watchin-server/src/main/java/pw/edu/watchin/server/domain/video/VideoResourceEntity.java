package pw.edu.watchin.server.domain.video;

import lombok.Getter;
import lombok.Setter;
import pw.edu.watchin.server.domain.resource.ResourceEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "video_resource", uniqueConstraints = @UniqueConstraint(columnNames = {"video_id", "quality"}))
public class VideoResourceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private VideoEntity video;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private ResourceEntity resource;

    @Enumerated(EnumType.STRING)
    @Column(name = "quality", nullable = false)
    private VideoQualityType quality;

    @Column(name = "ordinal", nullable = false)
    private int ordinal;
}
