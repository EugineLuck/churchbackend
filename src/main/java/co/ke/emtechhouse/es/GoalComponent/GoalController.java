package co.ke.emtechhouse.es.GoalComponent;

import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/goal")
public class GoalController {
    @Autowired
    GoalRepo goalRepo;
    @Autowired
    private GoalService goalService ;

    @PostMapping("/add")
    public ResponseEntity<?> newGoal(@RequestBody Goal goal) {
        try{
            ApiResponse response = goalService.addGoal(goal);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    @GetMapping("/get/by/id/{goalId}")
        public ResponseEntity<?> fetchGoalById(@PathVariable("goalId") Long goalId) {
        try{
            ApiResponse response = goalService.getGoal(goalId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }


}
