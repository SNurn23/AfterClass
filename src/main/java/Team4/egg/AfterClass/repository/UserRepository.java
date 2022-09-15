package Team4.egg.AfterClass.repository;

import Team4.egg.AfterClass.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.user_name = ?1 ")
    User existUser(String username);

    Optional<User> findByEmail(String email);

    @Query("SELECT u.member.id FROM User u WHERE u.id = ?1")
    int findIdMember(int idUser);

    @Query("SELECT u.id FROM User u WHERE u.member.id = ?1")
    int findIdUser(int idMember);



//    @Modifying
//    @Query("UPDATE User a SET a.deleted = false WHERE a.id = ?1")
//    void enableById(Integer id);

}
