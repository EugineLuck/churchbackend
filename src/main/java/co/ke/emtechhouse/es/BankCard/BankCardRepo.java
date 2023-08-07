package co.ke.emtechhouse.es.BankCard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankCardRepo extends JpaRepository <BankCard,Long>{
    Boolean existsByUsersFkAndBankFkAndCardNumberAndCvv(Long usersId, Long bankId, String cardNumber, String cvv);
    List<BankCard> findByDeletedFlag(Character deleteFlag);
    Optional<BankCard> findByDeletedFlagAndId(Character deleteFlag, Long id);
    List<BankCard> findByUsersFk(Long usersId);
}
