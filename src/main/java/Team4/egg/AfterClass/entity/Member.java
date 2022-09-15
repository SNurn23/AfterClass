package Team4.egg.AfterClass.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Member", indexes = {@Index(name = "idx_member_id", columnList = "id_member")})
public class Member implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_member", nullable = false)
    private Integer id;

    @Column(name = "full_name", nullable = false)
    private String full_name;

    @Column(name = "gender")
    private char gender;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "profile_img")
    private String profile_img;

    @Column(name = "cover_img")
    private String cover_img;

    @Column(name = "description",  length = 250)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)//ver
    @JoinColumn(name = "id_university", referencedColumnName = "id")
    private University university;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_career", referencedColumnName = "id")
    private Career career;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "member")
    private List<Post> posts;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "member")
    private List<Meeting> meetings;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "member")
    private List<StudyGroup_Member> member_group_list;
}
