package Team4.egg.AfterClass.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Id;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.EnumType.STRING;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member_study_group")
public class StudyGroup_Member implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "member", referencedColumnName = "id_member",nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "studyGroup", referencedColumnName = "id_group",nullable = false)
    private StudyGroup studyGroup;

    @Enumerated(STRING)
    @Column(name = "roleGroup", nullable = false)
    private RoleGroup roleGroup;





}
