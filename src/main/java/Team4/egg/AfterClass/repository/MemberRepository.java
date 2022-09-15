package Team4.egg.AfterClass.repository;

import Team4.egg.AfterClass.entity.Member;
import Team4.egg.AfterClass.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Integer> {


}
