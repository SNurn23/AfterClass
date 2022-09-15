package Team4.egg.AfterClass.repository;

import Team4.egg.AfterClass.entity.StudyGroup;
import Team4.egg.AfterClass.entity.StudyGroup_Member;
import Team4.egg.AfterClass.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyGroup_MemberRepository extends JpaRepository<StudyGroup_Member,Integer> {

    @Query("SELECT sg FROM StudyGroup_Member sgm " +
            "JOIN sgm.studyGroup sg " +
            "WHERE sgm.member.id = ?1")
    List<StudyGroup> findAllByMember(int idMember);

    @Query("SELECT u FROM StudyGroup_Member sgm " +
            "INNER JOIN sgm.member m ON m.id = sgm.member.id " +
            "INNER JOIN User u ON u.member.id = m.id " +
            "WHERE sgm.studyGroup.id = ?1")
    List<User> findAllMembersByGroup(int idMember);

    @Query("SELECT DISTINCT sg FROM StudyGroup_Member sgm " +
            "INNER JOIN sgm.studyGroup sg ON sg.id = sgm.studyGroup.id " +
            "WHERE sg.id NOT IN (SELECT sgm.studyGroup.id FROM StudyGroup_Member sgm " +
            "WHERE sgm.member.id = ?1 ) AND sgm.member.career.id = ?2")
    List<StudyGroup> findAllStudyGroupsAvailable(int idMember , int idCareer);

    @Query("SELECT COUNT(sgm.studyGroup.id) FROM StudyGroup_Member sgm " +
            "WHERE sgm.studyGroup.id = ?1")
    int countMembers(int idGroup);

    @Query("SELECT sgm FROM StudyGroup_Member sgm " +
            "WHERE sgm.roleGroup LIKE 'ADMIN' AND sgm.studyGroup.id = ?1")
    StudyGroup_Member findAdmin(int idGroup);

    @Query("SELECT sgm FROM StudyGroup_Member sgm " +
            "WHERE sgm.studyGroup.id = ?1")
    StudyGroup_Member findSGM(int idGroup);

    @Query("SELECT sgm FROM StudyGroup_Member sgm " +
            "WHERE sgm.member.id = ?1 AND sgm.studyGroup.id = ?2")
    StudyGroup_Member findSGMByMember(int idMember,int idGroup);




}
