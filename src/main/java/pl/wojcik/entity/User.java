package pl.wojcik.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false, unique = true)
    String login;
    @Column(nullable = false)
    String password;
    @Lob
    @Column(name = "hashed_password", nullable = false)
    byte[] hash_pass;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
    List<Post> posts = new LinkedList<>();


    public User(String login, String password) throws NoSuchAlgorithmException {
        this.login = login;
        this.password = password;
        this.hash_pass = hashPassword(password);
    }

    private byte[] hashPassword(String password_plaintext) throws NoSuchAlgorithmException {
        return (MessageDigest.getInstance("SHA-512").digest(password_plaintext.getBytes(StandardCharsets.UTF_8)));
    }

    public void addPost(Post post) {
        post.setUser(this);
        posts.add(post);
    }
}
