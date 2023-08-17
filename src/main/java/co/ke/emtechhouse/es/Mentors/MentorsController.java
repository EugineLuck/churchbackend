package co.ke.emtechhouse.es.Mentors;

import co.ke.emtechhouse.es.Advertisement.AdsService;
import co.ke.emtechhouse.es.Advertisement.Advertisement;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/v1/mentors")
public class MentorsController {
    @Autowired
    private MentorsService mentorsService;

    public  MentorsController(){

    }


    @PostMapping("/add")
    public ResponseEntity<Object> addMentor(@RequestBody Mentors mentor) {
        ApiResponse response = new ApiResponse();
        try {
            Mentors saveMentor = mentorsService.saveMentor(mentor);

            response.setMessage("Mentor added Successful");
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setEntity(saveMentor);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<Object> getAllMentors() {
        try {
            List<Mentors> allMentors = mentorsService.getAll();
            return new ResponseEntity<>(allMentors, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @GetMapping("/get/by/{id}")
    public ResponseEntity<Object> getByIdAd(Long id) {
        try {
            Mentors mentor = mentorsService.findById(id);
            return new ResponseEntity<>(mentor, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    //    Update Resource
//    @PutMapping("/update/advertisement/{Id}")
//    public ResponseEntity<?> updateAdvert(@PathVariable Long Id,
//
//                                          @RequestBody Advertisement advert) {
//        try {
//            ApiResponse response = mentorsService.updateMentor(advert, Id);
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            log.info("Catched Error {} " + e);
//            return null;
//        }
//    }




    //    Delete Resource
    @DeleteMapping("/delete/id")
    public ResponseEntity<Object> deleteById(Long id) {
        try {
            mentorsService.delete(id);

        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
        return null;
    }










}
