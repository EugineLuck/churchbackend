package co.ke.emtechhouse.es.Announcements;

import co.ke.emtechhouse.es.Auth.Members.MemberDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AnnouncementsRepo extends JpaRepository< Announcements, Long> {


    @Transactional
    @Query(nativeQuery = true,value = "SELECT * FROM announcements WHERE status = 1 ")
    List<Announcements> findAllActive();

    @Transactional
    @Query(nativeQuery = true,value = "SELECT * FROM announcements WHERE status = 0 ")
    List<Announcements> scheduled();

}
