package Team4.egg.AfterClass.repository;

import Team4.egg.AfterClass.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

    @Query("SELECT p FROM Post p WHERE p.studyGroup.id = ?1")
    List<Post> findAllByIdGroup(int idGroup);
}
