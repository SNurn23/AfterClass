package Team4.egg.AfterClass.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post", indexes = {@Index(name = "idx_post_id", columnList = "id")})
public class Post implements Comparable<Post> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "tittle", nullable = false)
    private String tittle;

    @Column(name = "content", length = 250)
    private String content;

    @Column(name = "file")//ver despues
    private String file;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_member", referencedColumnName = "id_member")
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_group", referencedColumnName = "id_group")
    private StudyGroup studyGroup;

    @Override
    public int compareTo(Post post) {
        return post.getId().compareTo(this.id);
    }

}