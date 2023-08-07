package co.ke.emtechhouse.es.Stages;

import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.Community.Community;
import co.ke.emtechhouse.es.Groups.Groups;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/Stages")
public class StagesController {
    @Autowired
    private StagesService stagesService;
    public StagesController(){
    }
    @PostMapping("/add")
    public ResponseEntity<Object> addStages(@RequestBody Stages stages) {
        try {
            ApiResponse<?> savedStages = stagesService.addStages(stages);
            return new ResponseEntity<>(savedStages, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }
    @GetMapping("/get/all")
    public ResponseEntity<?> fetchAll() {
        try{
            ApiResponse response = stagesService.getAllStages();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @GetMapping("/get/by/id")
    public ResponseEntity<Object> getByIdStages(Long id) {
        try {
            Stages allStages = stagesService.findById(id);
            return new ResponseEntity<>(allStages, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }
    @PutMapping("/delete/temp/")
    public ResponseEntity<Object> delete(Long id) {
        try {
            stagesService.delete(id);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
        return null;
    }

    @GetMapping("/get/by/memberNumber/{memberNumber}")
    public ResponseEntity<?> fetchStagesByMemberNumber(@PathVariable("memberNumber") String memberNumber) {
        try {
            ApiResponse response = stagesService.getStagesByMemberNumber(memberNumber);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

}
