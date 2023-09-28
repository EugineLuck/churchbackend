package co.ke.emtechhouse.es.Location;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WardRepo extends JpaRepository<Ward, Long> {
    Optional<Ward> findByWardName(String wardName);
}
