package Team4.egg.AfterClass.repository;

import Team4.egg.AfterClass.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Integer> {

//    @Modifying
//    @Query("UPDATE Subject a SET a.deleted = false WHERE a.id = ?1")
//    void enableById(Integer id);

    @Query("SELECT s FROM Subject s WHERE s.name = ?1 and s.career.id = ?2")
    Subject findByNameAndCareer(String name, Integer id);

    @Query("SELECT s FROM Subject s WHERE s.career.id = ?1")
    List<Subject> findAllByCareer(Integer id);
}
