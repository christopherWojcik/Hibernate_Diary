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
    @OneToMany(
            cascade = {CascadeType.REMOVE, CascadeType.PERSIST},
            mappedBy = "user",
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    Set<Post> posts = new LinkedHashSet<>();

    public User(String login, byte[] password){
        this.login = login;
        this.password = password;
    }
}
