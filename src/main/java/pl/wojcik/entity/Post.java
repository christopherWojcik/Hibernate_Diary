package pl.wojcik.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    Long id;

    @Getter
    @Setter
    @Column(nullable = false)
    String title;

    @Getter
    @Setter
    @Column(nullable = false)
    String content;

    @Getter
    @Setter(AccessLevel.PACKAGE)
    @ManyToOne
    User user;

    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

}
