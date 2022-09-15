package Team4.egg.AfterClass.repository;

import Team4.egg.AfterClass.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting,Integer> {

    @Query("SELECT m FROM Meeting m WHERE m.studyGroup.id = ?1")
    List<Meeting> findAllByIdGroup(int id_group);
}
