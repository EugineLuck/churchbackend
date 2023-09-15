package co.ke.emtechhouse.es.Events;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantsRepo extends JpaRepository<Participants,Long> {
    List<Participants> findByEvents(Optional<Events> events);

}
