package pl.wojcik.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    Long id;

    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    String login;

    @Lob
    @Getter
    @Setter
    @Column(nullable = false)
    byte[] password;

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
    Set<Post> posts = new LinkedHashSet<>();

    public User(String login, byte[] password) throws NoSuchAlgorithmException {
        this.login = login;
        this.password = password;
    }
}
