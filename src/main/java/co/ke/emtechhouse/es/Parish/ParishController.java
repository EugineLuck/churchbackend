package co.ke.emtechhouse.es.Parish;

import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.Community.Community;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ParishController {
    private final ParishService parishService;
    private final ParishRepository parishRepository;

    public ParishController(ParishService parishService, ParishRepository parishRepository) {
        this.parishService = parishService;
        this.parishRepository = parishRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addParish(@RequestBody Parish parish) {
        try {
            ApiResponse<?> savedParishes = parishService.addParish(parish);
            return new ResponseEntity<>(savedParishes, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> fetchAll() {
        try{
            ApiResponse response = parishService.getAllparish();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @GetMapping("/get/by/id")
    public ResponseEntity<Object> getByIdParish(Long id) {
        try {
            Parish allParish = parishService.findById(id);
            return new ResponseEntity<>(allParish, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }
    @PutMapping("/delete/temp/")
    public ResponseEntity<Object> delete(Long id) {
        try {
            parishService.delete(id);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
        return null;
    }
}
