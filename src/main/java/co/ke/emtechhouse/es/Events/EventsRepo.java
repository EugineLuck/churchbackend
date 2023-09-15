package co.ke.emtechhouse.es.Events;

import co.ke.emtechhouse.es.Announcements.Announcements;
import co.ke.emtechhouse.es.Giving.Giving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EventsRepo extends JpaRepository<Events,Long> {

    @Transactional
    @Query(nativeQuery = true,value = "SELECT * FROM events  WHERE status = 'On Going'")
    List<Events> activeEvents();
    @Transactional
    @Query(nativeQuery = true,value = "SELECT * FROM events  WHERE status = 'Upcoming'")
    List<Events> upcomingEvents();
    @Transactional
    @Query(nativeQuery = true,value = "SELECT * FROM events  WHERE status = 'Completed'")
    List<Events> completedEvents();
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "Delete from events  WHERE id = :id")
    void deleteEvent(

            @Param(value = "id") Long id
    );
}
