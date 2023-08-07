package co.ke.emtechhouse.es.PersonalDetails;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/PersonalDetails")
public class PersonalDetailsController {

    @Autowired
    private PersonalDetailsService personalDetailsService;
//    @PostMapping("/add")
//    public ResponseEntity<Object> addDetails(@RequestBody PersonalDetails personalDetails) {
//        try {
////            PersonalDetails savedDetails = personalDetailsService.savePersonalDetails(personalDetails);
////            return new ResponseEntity<>(savedDetails, HttpStatus.OK);
//        } catch (Exception e) {
//            log.info("Error" + e);
//            return null;
//        }
//    }
    @GetMapping("/get/all")
    public ResponseEntity<Object> getAllDetails() {
        try {
            List<PersonalDetails> allDetails = personalDetailsService.findAll();
            return new ResponseEntity<>(allDetails, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }
    @GetMapping("/get/by/id")
    public ResponseEntity<Object> getByIdDetails(Long id) {
        try {
            PersonalDetails allDetails= personalDetailsService.findById(id);
            return new ResponseEntity<>(allDetails, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }
    @PutMapping("/delete/temp/")
    public ResponseEntity<Object> delete(Long id) {
        try {
            personalDetailsService.delete(id);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
        return null;
    }
}