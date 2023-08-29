package co.ke.emtechhouse.es.AppUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface AuditingRepository extends JpaRepository<Auditing,Long> {
    //Select audit trails for one user
    @Transactional
    @Query(nativeQuery = true,value = "SELECT * FROM audittrails WHERE username = :username AND starttime LIKE %:starttime%")
    List<Auditing> todayTrails(@Param("username") String username, @Param("starttime") String starttime);

    //Select all trails for one user
    @Transactional
    @Query(nativeQuery = true,value = "SELECT * FROM audittrails WHERE username = :username")
    List<Auditing> allTrails(@Param("username") String username);
}
