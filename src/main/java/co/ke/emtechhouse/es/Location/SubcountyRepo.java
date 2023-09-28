package co.ke.emtechhouse.es.Location;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubcountyRepo extends JpaRepository<Subcounty, Long> {
    Optional<Subcounty> findBySubcountyName(String subcountyName);
}
