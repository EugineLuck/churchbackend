package co.ke.emtechhouse.es.Advertisement;


import co.ke.emtechhouse.es.Auth.Members.MemberUpdateDTO;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.Deanery.Deanery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/advertisements")
public class AdsController {

    @Autowired
    private AdsService AdsService;

    public  AdsController(){

    }

    @PostMapping("/add")
    public ResponseEntity<Object> addAdvert(@RequestBody Advertisement advert) {
        try {
            Advertisement savedAdvert = AdsService.saveAdvert(advert);
            return new ResponseEntity<>(savedAdvert, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<Object> getAllAdverts() {
        try {
            List<Advertisement> allAdverts= AdsService.getAll();
            return new ResponseEntity<>(allAdverts, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @GetMapping("/get/by/{id}")
    public ResponseEntity<Object> getByIdAd(Long id) {
        try {
            Advertisement ad = AdsService.findById(id);
            return new ResponseEntity<>(ad, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

//    Update Resource
@PutMapping("/update/advertisement/{Id}")
public ResponseEntity<?> updateAdvert(@PathVariable Long Id,

                                     @RequestBody Advertisement advert) {
    try {
        ApiResponse response = AdsService.updateAd(advert, Id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
        log.info("Catched Error {} " + e);
        return null;
    }
}




//    Delete Resource
    @DeleteMapping("/delete/id")
    public ResponseEntity<Object> delete(Long id) {
        try {
            AdsService.delete(id);

        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
        return null;
    }








}
