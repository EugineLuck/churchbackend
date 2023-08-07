package co.ke.emtechhouse.es.Giving;

import co.ke.emtechhouse.es.Auth.Members.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GivingRepo extends JpaRepository<Giving,Long> {
    @Transactional
    @Query(nativeQuery = true,value = "SELECT * FROM giving  WHERE status = 'On Going'")
    List<Giving> activeGiving();
    @Transactional
    @Query(nativeQuery = true,value = "SELECT * FROM giving  WHERE status = 'Upcoming'")
    List<Giving> upcomingGiving();
    @Transactional
    @Query(nativeQuery = true,value = "SELECT * FROM giving  WHERE status = 'Completed'")
    List<Giving> completedGiving();

}
