package Team4.egg.AfterClass.repository;

import Team4.egg.AfterClass.entity.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyGroupRepository extends JpaRepository<StudyGroup, Integer> {

    @Query("SELECT sg FROM StudyGroup sg WHERE sg.name = ?1 AND sg.subject.id = ?2 AND sg.subject.career.id = ?3")
    StudyGroup findByNameAndSubject(String name, int idSubject,int idCareer);

    @Query("SELECT sg FROM StudyGroup sg WHERE sg.subject.id = ?1")
    List<StudyGroup> findAllBySubject(int id);

     @Query("SELECT MAX(sg.id) FROM StudyGroup sg ")
     int findLastId ();

}
