package co.ke.emtechhouse.es.Giving;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE giving SET status = :status, target_amount = :targetAmount, description = :description, giving_title = :givingTitle, end_date = :endDate, start_date = :startDate, giving_level = :givingLevel WHERE id = :id")
    void updateGiving(
            @Param("id") Long id,
            @Param("givingLevel") String givingLevel,
            @Param("givingTitle") String givingTitle,
            @Param("targetAmount") Double targetAmount,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,

            @Param("description") String description,

            @Param("status") String status
    );
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "Delete from giving  WHERE id = :id")
    void deleteGiving(

            @Param(value = "id") Long id
    );

}
