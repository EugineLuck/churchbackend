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


    @Transactional
    @Query(nativeQuery = true,value = "SELECT CONCAT(m.first_name, ' ', m.last_name) AS fullName,m.member_number as memberNumber,g.giving_level as level,g.giving_title as title, CONCAT_WS(' ',  COALESCE(t.envelope_number, ''),COALESCE(t.cheque_number, ''), COALESCE(t.transaction_number, '')) AS  number,t.transaction_amount as amount,t.transaction_mode as transactionMode, DATE(t.date_paid) as postedTime,t.id as transId,g.id as givingId FROM `transaction` t join giving g on g.id = t.giving_id join members m on m.member_number=t.member_number where  t.result_code = 0 group by t.id")
    List<SuccessfullyTransactions> findAllTransations();
  @Transactional
  @Query(nativeQuery = true, value = "SELECT CONCAT(m.first_name, ' ', m.last_name) AS fullName, m.member_number as memberNumber, g.giving_level as level, g.giving_title as title, CONCAT_WS(' ', COALESCE(t.envelope_number, ''), COALESCE(t.cheque_number, ''), COALESCE(t.transaction_number, '')) AS number, t.transaction_amount as amount, t.transaction_mode as transactionMode, t.transaction_date as postedTime, t.id as transId, g.id as givingId FROM transaction t JOIN giving g ON g.id = t.giving_id JOIN members m ON m.member_number = t.member_number WHERE t.result_code = 0 AND (:fromDate IS NULL OR t.transaction_date >= :fromDate) AND (:toDate IS NULL OR t.transaction_date <= :toDate) AND ((:churchId IS NULL AND :memberNumber IS NULL AND :familyId IS NULL AND :communityId IS NULL AND :groupsId IS NULL) OR (t.church_id = :churchId OR t.member_number = :memberNumber OR t.family_id = :familyId OR t.community_id = :communityId OR t.groups_id = :groupsId)) GROUP BY t.id")
  List<SuccessfullyTransactions> findAllFilteredTransations(
          @Param("fromDate") Date fromDate,
          @Param("toDate") Date toDate,
          @Param("churchId") Long churchId,
          @Param("memberNumber") String memberNumber,
          @Param("familyId") Long familyId,
          @Param("communityId") Long communityId,
          @Param("groupsId") Long groupsId
  );



    @Transactional
    @Query(nativeQuery = true,value = "SELECT CONCAT(m.first_name, ' ', m.last_name) AS fullName,m.member_number as memberNumber,g.giving_level as level,g.giving_title as title, IFNULL(CONCAT(t.transaction_number, ' ', t.envelope_number, ' ', t.cheque_number), '') AS  number,t.transaction_amount as amount,t.transaction_mode as transactionMode, DATE(t.date_paid) as postedTime,t.id as transId,g.id as givingId FROM `transaction` t join giving g on g.id = t.giving_id join members m on m.member_number=t.member_number where  t.result_code = 0 group by t.id")



    List<SuccessfullyTransactions> getAllSucessfullyTransactions();
  @Transactional
    @Query(nativeQuery = true,value = "SELECT CONCAT(m.first_name, ' ', m.last_name) AS fullName,m.member_number as memberNumber,g.giving_level as level,g.giving_title as title, t.transaction_number as number,t.transaction_amount as amount,t.transaction_mode as transactionMode,DATE(t.date_paid) as postedTime,t.id as transId,g.id as givingId FROM `transaction` t join giving g on g.id = t.giving_id join members m on m.member_number=t.member_number where t.member_number=:memberNumber AND t.result_code = 0")

    List<SuccessfullyTransactions>  findByMemberNumber(@Param(value = "memberNumber")String memberNumber);


    @Transactional
    @Query(nativeQuery = true,value = "SELECT * FROM `transaction` where member_number=:memberNumber  AND result_code = 0")
    List<SuccessfullyTransactions>  findByUssdMemberNumber(@Param(value = "memberNumber")String memberNumber);



  @Transactional
    @Query(nativeQuery = true,value = "SELECT CONCAT(m.first_name, ' ', m.last_name) AS fullName,m.member_number as memberNumber,g.giving_level as level,g.giving_title as title, IFNULL(CONCAT(t.transaction_number, ' ', t.envelope_number, ' ', t.cheque_number), '') AS  number,t.transaction_amount as amount,t.transaction_mode as transactionMode, DATE(t.date_paid) as postedTime,t.id as transId,g.id as givingId FROM `transaction` t join giving g on g.id = t.giving_id join members m on m.member_number=t.member_number where   t.result_code = 0 group by t.id")
    List<SuccessfullyTransactions> findByGivingId(@Param(value = "givingId")Long givingId);

    @Transactional
    @Query(nativeQuery = true, value = "SELECT CONCAT(m.first_name, ' ', m.last_name) AS fullName, m.member_number AS memberNumber, g.giving_level AS level, g.giving_title AS title, CONCAT_WS(' ', COALESCE(t.envelope_number, ''), COALESCE(t.cheque_number, ''), COALESCE(t.transaction_number, '')) AS number, t.transaction_amount AS amount, t.transaction_mode AS transactionMode, DATE(t.date_paid) AS postedDate, t.id AS transId, g.id AS givingId FROM `transaction` t JOIN giving g ON g.id = t.giving_id JOIN members m ON m.member_number = t.member_number WHERE (t.result_code = 0 AND DATE(t.date_paid) BETWEEN :startDate AND :endDate ) " +
            "AND (:memberNumber IS NULL OR t.member_number = :memberNumber) " +
            "AND (:communityID IS NULL OR m.community_id = :communityID) " +
            "AND (:familyID IS NULL OR m.family_id = :familyID) " +
            "AND (:churchID IS NULL OR m.out_station_id = :churchID) " +
            "GROUP BY t.id;")
    List<SuccessfullyTransactions> fetchByDateRange(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("memberNumber") Integer memberNumber,
            @Param("communityID") Integer communityID,
            @Param("familyID") Integer familyID,
            @Param("churchID") Integer churchID
    );


}
