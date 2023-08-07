package co.ke.emtechhouse.es.Career;


import co.ke.emtechhouse.es.Advertisement.Advertisement;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/careers")
public class CareerController {
    @Autowired
    private CareerService CareerService;

    public  CareerController(){

    }

    @PostMapping("/add")
    public ResponseEntity<Object> addAdvert(@RequestBody Career career) {
        try {
            Career savedCareers = CareerService.saveCareer(career);
            return new ResponseEntity<>(savedCareers, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<Object> getAllCareers() {
        try {
            List<Career> allCareers= CareerService.getAll();
            return new ResponseEntity<>(allCareers, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @GetMapping("/get/by/{id}")
    public ResponseEntity<Object> getByIdCareer(Long id) {
        try {
            Career career = CareerService.findById(id);
            return new ResponseEntity<>(career, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @PutMapping("/update/career/{Id}")
    public ResponseEntity<?> updateCareer(@PathVariable Long Id,

                                          @RequestBody Career career) {
        try {
            ApiResponse response = CareerService.updateCareer(career, Id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    @DeleteMapping("/delete/id")
    public ResponseEntity<Object> delete(Long id) {
        try {
            CareerService.delete(id);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
        return null;
    }










}
