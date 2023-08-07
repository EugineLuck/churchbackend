package co.ke.emtechhouse.es.HelpComponent.ContactInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepo extends JpaRepository<Contact,Long> {
    Optional<Contact> findByEmail(String email);
}
