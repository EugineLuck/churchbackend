package co.ke.emtechhouse.es.GoalComponent;

import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Auth.Members.MembersRepository;
import co.ke.emtechhouse.es.Auth.utils.CONSTANTS;
import co.ke.emtechhouse.es.Auth.utils.HttpInterceptor.UserRequestContext;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.Family.Family;
import co.ke.emtechhouse.es.Family.FamilyMember.FamilyMember;
import co.ke.emtechhouse.es.Family.FamilyMember.FamilyMemberRepo;
import co.ke.emtechhouse.es.Family.FamilyRepository;
import co.ke.emtechhouse.es.GoalComponent.GivingScheduleComponent.GivingSchedule;
import co.ke.emtechhouse.es.GoalComponent.GivingScheduleComponent.GivingScheduleRepo;
import co.ke.emtechhouse.es.Groups.GroupMemberComponent.GroupMember;
import co.ke.emtechhouse.es.Groups.GroupMemberComponent.GroupMemberRepo;
import co.ke.emtechhouse.es.Groups.Groups;
import co.ke.emtechhouse.es.Groups.GroupsRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
public class GoalService {

    @Autowired
    GoalRepo goalRepo;
    @Autowired
    GroupsRepo groupsRepo;
    @Autowired
    FamilyRepository familyRepo;
    @Autowired
    FamilyMemberRepo familyMemberRepo;
    @Autowired
    GroupMemberRepo groupMemberRepo;
    @Autowired
    MembersRepository membersRepository;
    @Autowired
    GivingScheduleRepo givingScheduleRepo;

    public ApiResponse<Goal> addGoal(Goal goal) {
        try {
            ApiResponse response = new ApiResponse();
            String MemberNo = "";
            LocalDate startDate = goal.getStartDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            LocalDate endDate = goal.getEndDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            long days_difference = ChronoUnit.DAYS.between(startDate, endDate);
            if (goal.getGivingFrequency() == GivingFrequency.WEEKLY && days_difference < 7) {
                response.setMessage("The start date and end date do not add up to a week");
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
            } else if (goal.getGivingFrequency() == GivingFrequency.MONTHLY && days_difference < 28) {
                response.setMessage("The start date and end date do not add up to a month");
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
            } else {
                goal.setNextGivingDate(goal.getStartDate());
            goal.setGivingPeriod(String.valueOf(days_difference));
//            goal.setRemainingAmount(goal.getTargetAmount() - goal.getGivingAmount());
            goal.setGivingSchedules(goal.getGivingSchedules());
            goal.setMemberNumber(goal.getMemberNumber());
//            TODO; Check if MEMBER is present
            String memberNumber = new String(goal.getMemberNumber());


            Optional<Members> memberCheck = membersRepository.findByMemberNumber(memberNumber);
            if (memberCheck.isPresent()) {

                //            TODO: Check if personal is present
                if (goal.getGoalType() == GoalType.PERSONAL) {
                    goal.setDeletedFlag(CONSTANTS.NO);
                    System.out.println("szasdadcsadd");
                    List<GivingSchedule> givingScheduleList = initializePersonalGivingSchedule(goal);
                    System.out.println("werrehnffjjhrth" + givingScheduleList);
                    goal.setGivingSchedules(givingScheduleList);
                    Goal savedGoal = goalRepo.save(goal);
                    response.setMessage(HttpStatus.CREATED.getReasonPhrase());
                    response.setMessage("GOAL " + goal.getGivingGoalName() + " CREATED SUCCESSFULLY ");
                    response.setStatusCode(HttpStatus.CREATED.value());
                    response.setEntity(savedGoal);
                } else {
                    response.setMessage("PERSONAL " + HttpStatus.NOT_FOUND.getReasonPhrase());
                    response.setStatusCode(HttpStatus.NOT_FOUND.value());
                }
            } else {
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("MEMBER" + HttpStatus.NOT_FOUND.getReasonPhrase());
            }
        }
            return response;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    public ApiResponse<Goal> getGoal(Long id) {
        try {
            ApiResponse response = new ApiResponse();
            if (UserRequestContext.getCurrentUser().isEmpty()) {
                response.setMessage("User name not present in the Request Header");
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                return response;
            } else {
                Optional<Goal> goal = goalRepo.findByDeletedFlagAndId(CONSTANTS.NO, id);
                if (goal.isPresent()) {
                    Goal goal1 = goal.get();
//                    goal1.setFamilyMember(goal.get().getFamilyMember());
                    goal1.setGivingSchedules(goal1.getGivingSchedules());
                    response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                    response.setStatusCode(HttpStatus.FOUND.value());
                    response.setEntity(goal1);
                    return response;
                } else {
                    response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                    response.setStatusCode(HttpStatus.NOT_FOUND.value());
                    return response;
                }
            }
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

//    public class GoalDTOByFamilyFk(Long familyId){
//        private List<String> familyGoals;
//        private Map<String, List<String>> familyMemberGoals;
//
//        public GoalDTOByFamilyFk(List<String> familyGoals, Map<String, List<String>> familyMemberGoals) {
//            this.familyGoals = familyGoals;
//            this.familyMemberGoals = familyMemberGoals;
//        }
//
//        public List<String> getFamilyGoals() {
//            return familyGoals;
//        }
//
//        public Map<String, List<String>> getFamilyMemberGoals() {
//            return familyMemberGoals;
//        }
//    }


    public List<GivingSchedule> initializePersonalGivingSchedule(Goal goal) {
        try {
//            TODO: CALCULATE PERIOD AND AMOUNT PER PERIOD
            /*
             * WEEKLY
             * DAILY
             * MONTHLY
             * */
            int days = 0;
            if (goal.getGivingFrequency() == GivingFrequency.WEEKLY) {
                days = 7;
            } else if (goal.getGivingFrequency() == GivingFrequency.MONTHLY) {
                days = 28;
            } else {
                days = 0;
            }
//
            // Convert the Date objects to milliseconds
            long milliseconds1 = goal.getStartDate().getTime();
            long milliseconds2 = goal.getEndDate().getTime();
// Calculate the difference between the two dates in milliseconds
            long diffMilliseconds = milliseconds2 - milliseconds1;
// Convert the difference to days
            long period = diffMilliseconds / (24 * 60 * 60 * 1000);
            long savingTimes = period / days;
            Calendar calendar = Calendar.getInstance();
            Double savingFreqAmount = goal.getTargetAmount() / savingTimes;


            System.out.println("das" + savingFreqAmount);
            List<GivingSchedule> givingScheduleList = new ArrayList<>();
            if (goal.getGoalType() == GoalType.PERSONAL) {
                Date startDate = goal.getStartDate();
                calendar.setTime(startDate);
                for (int i = 0; i < savingTimes; i++) {
                    calendar.add(Calendar.DATE, days);
                    startDate = calendar.getTime();
                    GivingSchedule givingSchedule = new GivingSchedule();
                    givingSchedule.setAmount(savingFreqAmount);
                    givingSchedule.setAmountBalance(0.00);
                    givingSchedule.setDateOfGiving(startDate);
                    givingSchedule.setAmountDue(0.00);
                    givingSchedule.setAmountBalance(0.00);
                    givingSchedule.setDateOfGiving(startDate);
                    givingSchedule.setGoalType(GoalType.PERSONAL);
//                    givingSchedule.setMemberNumber(goal.getMemberNumber());
                    givingSchedule.setStatus("Active");
                    givingScheduleList.add(givingSchedule);
                }
            }
            System.out.println("s" + givingScheduleList.size());
            return givingScheduleList;
        } catch (Exception e) {
            return null;
        }
    }


}
