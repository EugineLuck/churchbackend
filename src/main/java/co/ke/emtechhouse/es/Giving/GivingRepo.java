package co.ke.emtechhouse.es.Giving;

import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.MpesaIntergration.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GivingRepo extends JpaRepository<Giving,Long> {
    @Transactional
    @Query(nativeQuery = true,value = "SELECT * FROM giving  WHERE status = 'On Going'")
    List<Giving> activeGiving();
    @Transactional
    @Query(nativeQuery = true,value = "SELECT * FROM giving  WHERE status = 'Upcoming'")
    List<Giving> upcomingGiving();
    @Transactional
    @Query(nativeQuery = true,value = "SELECT * FROM giving  WHERE status = 'Completed'")
    List<Giving> completedGiving();

    @Transactional
    @Query(nativeQuery = true, value = "SELECT CONCAT(m.first_name, ' ', m.last_name) AS fullname, m.member_number AS memberNumber, g.giving_level AS level, g.giving_title AS title, t.phone_number AS number, t.transaction_amount AS amount, t.transaction_date AS postedTime, t.id AS transId, g.id AS givingId FROM `transaction` t JOIN giving g ON g.id = t.giving_id JOIN members m ON m.member_number = t.member_number JOIN member_groups mg ON mg.member_id = m.id WHERE mg.group_id = :groupId")
    List<Object[]> transactionsPerGroup(@Param("groupId") Long groupId);



}
