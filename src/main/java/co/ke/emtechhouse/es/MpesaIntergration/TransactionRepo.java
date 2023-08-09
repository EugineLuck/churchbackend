package co.ke.emtechhouse.es.MpesaIntergration;

import co.ke.emtechhouse.es.Auth.Members.MemberDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByMemberNumber(String memberNumber);
    Optional<Transaction> findByPhoneNumber(String phoneNumber);
    @Transactional
    @Query(nativeQuery = true,value = "SELECT CONCAT(m.first_name, ' ', m.last_name) AS fullName,m.member_number as memberNumber,g.giving_level as level,g.giving_title as title,t.phone_number as number,t.transaction_amount as amount,t.transaction_date as postedTime,t.id as transId,g.id as givingId FROM `transaction` t join giving g on g.id = t.giving_id join members m on m.member_number=t.member_number")
    List<SuccessfullyTransactions> getAllSucessfullyTransactions();


}
