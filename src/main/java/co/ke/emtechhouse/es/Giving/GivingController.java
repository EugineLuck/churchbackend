package co.ke.emtechhouse.es.Giving;

import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.OutStation.OutStation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/Giving")
public class GivingController {
    @Autowired
    private GivingService givingService;

    public GivingController() {
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addGiving(@RequestBody Giving giving) {
        try {
            ApiResponse<?> savedGiving = givingService.addGiving(giving);
            return new ResponseEntity<>(savedGiving, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> fetchAll() {
        try{
            ApiResponse response = givingService.getAllGiving();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    } @GetMapping("/get/Active")
    public ResponseEntity<?> fetchActive() {
        try{
            ApiResponse response = givingService.getActiveGiving();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }@GetMapping("/get/Upcoming")
    public ResponseEntity<?> fetchUpcoming() {
        try{
            ApiResponse response = givingService.getUpcomingGiving();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }@GetMapping("/get/Clossed")
    public ResponseEntity<?> fetchClossed() {
        try{
            ApiResponse response = givingService.getClossedGiving();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    @GetMapping("/get/by/id")
    public ResponseEntity<Object> getByIdGiving(Long id) {
        try {
            Giving allGiving = givingService.findById(id);
            return new ResponseEntity<>(allGiving, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @PutMapping("/delete/temp/")
    public ResponseEntity<Object> delete(Long id) {

        try {
            givingService.delete(id);

        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
        return null;
    }
}
