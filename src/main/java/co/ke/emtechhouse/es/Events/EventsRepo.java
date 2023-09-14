package co.ke.emtechhouse.es.Events;

import co.ke.emtechhouse.es.Announcements.Announcements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EventsRepo extends JpaRepository<Events,Long> {

    @Transactional
    @Query(nativeQuery = true,value = "SELECT * FROM events WHERE status = 1 ")
    Events fetchEvents();
}
