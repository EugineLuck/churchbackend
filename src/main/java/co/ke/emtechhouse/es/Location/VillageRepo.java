package co.ke.emtechhouse.es.Location;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VillageRepo extends JpaRepository<Village, Long> {
    Optional<Village> findByVillageName(String villageName);
}
