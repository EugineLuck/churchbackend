package co.ke.emtechhouse.es.NotificationComponent.TokenComponent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<Token, Long> {

    Optional<Token> findById(Long id);

    Optional<Token> findByMemberNumber(String memberNumber);
}
