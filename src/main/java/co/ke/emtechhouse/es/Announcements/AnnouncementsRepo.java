package co.ke.emtechhouse.es.Announcements;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementsRepo extends JpaRepository< Announcements, Long> {
    List<Announcements> findAll();

}
