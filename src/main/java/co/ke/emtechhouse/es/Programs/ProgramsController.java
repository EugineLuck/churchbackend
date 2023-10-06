package co.ke.emtechhouse.es.Programs;

import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.Parish.Parish;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/programs")
public class ProgramsController {

    @Autowired
    ProgramsRepo programsRepo;

public  ProgramsController(){

}

    @PostMapping("/add")
    public ResponseEntity<Object> addProgram(@RequestBody Programs program) {
    ApiResponse response = new ApiResponse<>();
        try {
            Programs save = programsRepo.save(program);
            response.setMessage("Created");
            response.setStatusCode(201);
            response.setEntity(save);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> fetchAll() {
        ApiResponse response = new ApiResponse<>();
        try{
            List<Programs> allSaved = programsRepo.findAll();
            response.setMessage("Found");
            response.setStatusCode(201);
            response.setEntity(allSaved);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }











}
