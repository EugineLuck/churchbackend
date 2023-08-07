package co.ke.emtechhouse.es.Deanery;

import co.ke.emtechhouse.es.Diocese.Diocese;
import co.ke.emtechhouse.es.Diocese.DioceseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/Deanery")
public class DeaneryController {
    @Autowired
    private DeaneryService deaneryService;
    public DeaneryController(){
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addDeanery(@RequestBody Deanery deanery) {
        try {
            Deanery savedDeanery = deaneryService.saveDeanery(deanery);
            return new ResponseEntity<>(savedDeanery, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }
    @GetMapping("/get/all")
    public ResponseEntity<Object> getAllDeanery() {
        try {
            List<Deanery> allDeanery= deaneryService.findAll();
            return new ResponseEntity<>(allDeanery, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }
    @GetMapping("/get/by/id")
    public ResponseEntity<Object> getByIdDeanery(Long id) {
        try {
            Deanery allDeanery = deaneryService.findById(id);
            return new ResponseEntity<>(allDeanery, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }
    @PutMapping("/delete/temp/")
    public ResponseEntity<Object> delete(Long id) {
        try {
            deaneryService.delete(id);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
        return null;
    }



}
