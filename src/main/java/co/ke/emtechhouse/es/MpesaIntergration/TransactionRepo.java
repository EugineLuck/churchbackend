package co.ke.emtechhouse.es.MpesaIntergration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByMerchantRequestIDAndCheckoutRequestID(String merchantRequestID, String checkoutRequestID);


    List<Transaction> findByMemberNumber(String memberNumber);


//    Optional<Transaction> findByRequestId(String requestId);

    @Query(value = "SELECT * FROM transaction  WHERE member_number = :memberNumber AND giving_id = :givingId AND status = :status", nativeQuery = true )
    List<Transaction> findByMemberNumberAndGivingIdAndStatus(@Param("memberNumber")String memberNumber, @Param("givingId")Long givingId, @Param("status")String status);

}
