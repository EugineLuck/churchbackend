package co.ke.emtechhouse.es.GoalComponent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface GoalRepo extends JpaRepository<Goal, Long> {
//    List<Goal> findByDeletedFlag(Character deletedFlag);
    Optional<Goal> findByDeletedFlagAndId(Character deletedFlag, Long id);
    List<Goal> findByGoalTypeAndMemberNumberAndDeletedFlag(GoalType goalType, Long usersFk, Character deletedFlag);

//    List<Goal> findByGoalTypeAndUsersFkAndFamilyFkAndGroupFkAndDeletedFlag(GoalType goalType, Long usersFk, Long familyFk, Long groupFk, Character deletedFlag);

    //    Fetch Functions

//    List<Goal> findByGoalTypeAndUsersFkAndGroupFkAndDeletedFlag(GoalType goalType, Long usersFk, Long groupFk, Character deletedFlag);
    List<Goal> findByMemberNumberAndGoalType(String memberNumber, GoalType goalType);
//    List<Goal> findByGoalTypeAndUsersFkAndFamilyFkAndDeletedFlag(GoalType goalType, Long usersFk, Long familyFk, Character deletedFlag);
//    List<Goal> findByGoalTypeAndUsersFkAndFamilyFkAndFamilyMemberFkAndDeletedFlag(GoalType goalType, Long usersFk, Long familyFk, Long familyMemberFk, Character deletedFlag);

    @Query(value = "SELECT * FROM `goal` WHERE date(`next_giving_date`)<=:date", countQuery = "select 1", nativeQuery = true)
    List<Goal> findAllGoalsByDate(Date date);

//      goal-->goal_schedule-->dateOfSaving == Today
//    @Query(value = "SELECT g.* FROM `goal` g JOIN `saving_schedule` s ON g.id = s.goal_fk WHERE date(s.`date_of_saving`) = :date",countQuery = "select 1", nativeQuery = true)
//    List<Goal> findAllGoalsByDate(Date date);


}

