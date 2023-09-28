package co.ke.emtechhouse.es.Location;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConstituencyRepo extends JpaRepository<Constituency, Long> {
    Optional<Constituency> findByConstituencyName(String constituencyName);
}
