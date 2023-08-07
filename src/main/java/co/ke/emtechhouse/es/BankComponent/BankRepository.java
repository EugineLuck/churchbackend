package co.ke.emtechhouse.es.BankComponent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

    List<Bank> findByDeletedFlag(Character deleteFlag);
    Optional<Bank> findByDeletedFlagAndId(Character deleteFlag, Long id);
    Boolean existsByBankName(String bankName);
    Boolean existsByBackgroundImage(String backgroundImage);
}

