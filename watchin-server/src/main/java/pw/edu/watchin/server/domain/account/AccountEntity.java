package pw.edu.watchin.server.domain.account;

import lombok.Getter;
import lombok.Setter;
import pw.edu.watchin.server.domain.channel.ChannelEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "account")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "activated", nullable = false)
    private boolean activated;

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
    private ChannelEntity channel;
}
