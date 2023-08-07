package co.ke.emtechhouse.es.GoalComponent.GivingScheduleComponent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface GivingScheduleRepo extends JpaRepository<GivingSchedule, Long> {

    @Query(value = "select * from giving_schedule where giving_schedule.goal_fk=:goalId and status='active' limit 1", nativeQuery = true)
    List<GivingSchedule> findByGoalFk(Long goalId);


    @Query(value = "select * from giving_schedule where giving_schedule.goal_fk=:goalId and giving_schedule.memberNumber=:memberNumber", nativeQuery = true)
    List<GivingSchedule> findByGoalFkAndMemberNumber(Long goalId, String memberNumber);

    @Query(value = "SELECT * FROM giving_schedule WHERE saved = TRUE", nativeQuery = true)
    List<GivingSchedule> findSavedGoals();

    Optional<GivingSchedule> findByDateOfGiving(Date dateOfGiving);

    @Query(value = "SELECT * FROM giving_schedule JOIN goal ON goal.id WHERE giving_schedule.goal_fk = 2;", nativeQuery = true)
    List<GivingSchedule> findGoalSchedules(Long goalFk);
}
