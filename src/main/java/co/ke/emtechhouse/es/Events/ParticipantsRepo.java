package co.ke.emtechhouse.es.Events;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantsRepo extends JpaRepository<Participants,Long> {
}
