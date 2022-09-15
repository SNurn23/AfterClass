package Team4.egg.AfterClass.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user", indexes = {@Index(name = "idx_user_user_name", columnList = "user_name")})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "user_name", length = 60, unique = true, nullable = false)
    private String user_name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)/////////////////ACTUALIZADOOOOOOOOOOOO
    @JoinColumn(name = "id_member", referencedColumnName = "id_member", nullable = false)
    private Member member;

    @Enumerated(STRING)
    @Column(name = "role", nullable = false)
    private Role role;


}
