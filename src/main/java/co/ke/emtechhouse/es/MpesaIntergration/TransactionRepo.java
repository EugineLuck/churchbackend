package co.ke.emtechhouse.es.MpesaIntergration;

import co.ke.emtechhouse.es.Auth.Members.MemberDetails;
import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.Stages.Stages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {

//    List<SuccessfullyTransactions> findByMemberNumber(String memberNumber);
//
//    List<SuccessfullyTransactions> findByGivingId(Long givingId);

    @Transactional
    @Query(nativeQuery = true,value = "SELECT CONCAT(m.first_name, ' ', m.last_name) AS fullName,m.member_number as memberNumber,g.giving_level as level,g.giving_title as title, CONCAT_WS(' ',  COALESCE(t.envelope_number, ''),COALESCE(t.cheque_number, ''), COALESCE(t.transaction_number, '')) AS  number,t.transaction_amount as amount,t.transaction_mode as transactionMode, t.transaction_date as postedTime,t.id as transId,g.id as givingId FROM `transaction` t join giving g on g.id = t.giving_id join members m on m.member_number=t.member_number where  t.result_code = 0 group by t.id")
    List<SuccessfullyTransactions> findAllTransations();



    @Transactional
    @Query(nativeQuery = true,value = "SELECT CONCAT(m.first_name, ' ', m.last_name) AS fullName,m.member_number as memberNumber,g.giving_level as level,g.giving_title as title, IFNULL(CONCAT(t.transaction_number, ' ', t.envelope_number, ' ', t.cheque_number), '') AS  number,t.transaction_amount as amount,t.transaction_mode as transactionMode, t.transaction_date as postedTime,t.id as transId,g.id as givingId FROM `transaction` t join giving g on g.id = t.giving_id join members m on m.member_number=t.member_number where  t.result_code = 0 group by t.id")
    List<SuccessfullyTransactions> getAllSucessfullyTransactions();
  @Transactional

    @Query(nativeQuery = true,value = "SELECT CONCAT(m.first_name, ' ', m.last_name) AS fullName,m.member_number as memberNumber,g.giving_level as level,g.giving_title as title, t.transaction_number as number,t.transaction_amount as amount,t.transaction_mode as transactionMode,t.transaction_date as postedTime,t.id as transId,g.id as givingId FROM `transaction` t join giving g on g.id = t.giving_id join members m on m.member_number=t.member_number where t.member_number=:memberNumber AND t.result_code = 0")

    List<SuccessfullyTransactions>  findByMemberNumber(@Param(value = "memberNumber")String memberNumber);


    @Transactional
    @Query(nativeQuery = true,value = "SELECT * FROM `transaction` where member_number=:memberNumber  AND result_code = 0")
    List<SuccessfullyTransactions>  findByUssdMemberNumber(@Param(value = "memberNumber")String memberNumber);



  @Transactional

    @Query(nativeQuery = true,value = "SELECT CONCAT(m.first_name, ' ', m.last_name) AS fullName,m.member_number as memberNumber,g.giving_level as level,g.giving_title as title, IFNULL(CONCAT(t.transaction_number, ' ', t.envelope_number, ' ', t.cheque_number), '') AS  number,t.transaction_amount as amount,t.transaction_mode as transactionMode, t.transaction_date as postedTime,t.id as transId,g.id as givingId FROM `transaction` t join giving g on g.id = t.giving_id join members m on m.member_number=t.member_number where   t.result_code = 0 group by t.id")
    List<SuccessfullyTransactions> findByGivingId(@Param(value = "givingId")Long givingId);

//    List<Transaction> fetchByDateRange(String dateFormat, String dateFormat1);


    @Transactional
    @Query(nativeQuery = true, value = "SELECT * FROM transaction where transaction_date BETWEEN :startDate and :endDate")
//    @Query(nativeQuery = true, value = "SELECT CONCAT(m.first_name, ' ', m.last_name) AS fullName, m.member_number as memberNumber, g.giving_level as level, g.giving_title as title, CONCAT_WS(' ', COALESCE(t.envelope_number, ''), COALESCE(t.cheque_number, ''), COALESCE(t.transaction_number, '')) AS number, t.transaction_amount as amount, t.transaction_mode as transactionMode, t.transaction_date as postedTime, t.id as transId, g.id as givingId FROM `transaction` t join giving g on g.id = t.giving_id join members m on m.member_number = t.member_number where t.result_code = 0 AND t.transaction_date between :fromDate and  :toDate ")
    List<Transaction> fetchByDateRange(@Param("startDate") String startDate, @Param("endDate") String endDate);



}
