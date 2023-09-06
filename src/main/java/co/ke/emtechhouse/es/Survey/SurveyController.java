package co.ke.emtechhouse.es.Survey;


import co.ke.emtechhouse.es.Auth.Requests.GivingRequest;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping(path = "/api/v1/survey/")
public class SurveyController {

    @Autowired
    SurveyRepository surveyRepository;

    public  SurveyController(){

    }

    @PostMapping("/add")
    public ResponseEntity<?> addSurvey(@Valid @RequestBody Survey survey) throws MessagingException {
        ApiResponse response = new ApiResponse();

        try{
            survey.setDateCreated(new Date());
            Survey saveSurvey = surveyRepository.save(survey);
            response.setMessage("Survey Saved");
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setEntity(saveSurvey);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }

    }


    @GetMapping("/fetch")
    public ResponseEntity<?> fetchAll() throws MessagingException {
        ApiResponse response = new ApiResponse();
        try{
            List<Survey> allSaved = surveyRepository.findAll();
            response.setMessage("Found");
            response.setStatusCode(HttpStatus.FOUND.value());
            response.setEntity(allSaved);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }

    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<?> fetchById(@PathVariable Long id) throws MessagingException {
        ApiResponse response = new ApiResponse();
        try{
            Optional<Survey> savedSurvey = surveyRepository.findById(id);
            if(savedSurvey.isPresent()){
                Survey existing = savedSurvey.get();
                response.setMessage("Found");
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(existing);
            }else{
                response.setMessage("Survey with ID Not Found");
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setEntity("");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> fetchById(@PathVariable Long id, @RequestBody Survey survey) throws MessagingException {
        ApiResponse response = new ApiResponse();
        try{
            Optional<Survey> savedSurvey = surveyRepository.findById(id);
            if(savedSurvey.isPresent()){
                Survey existing = savedSurvey.get();
                existing.setAge(survey.getAge());
                existing.setGender(survey.getGender());
                existing.setContactMethod(survey.getContactMethod());
                existing.setAttendanceFrequency(survey.getAttendanceFrequency());
                existing.setChurchAttendanceDuration(survey.getChurchAttendanceDuration());
                existing.setMostEnjoyed(survey.getMostEnjoyed());
                existing.setThemesForDiscusion(survey.getThemesForDiscusion());
                existing.setWorshipSatification(survey.getWorshipSatification());
//                Save to DB
                Survey updateData = surveyRepository.save(existing);

                response.setMessage("Survey Updated Successfully");
                response.setStatusCode(HttpStatus.CREATED.value());
                response.setEntity(updateData);


            }else{
                response.setMessage("SUrvey with ID Not Found");
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setEntity("");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteSurvey(@PathVariable Long id){
        ApiResponse response = new ApiResponse();
        try{
            Optional<Survey> savedSurvey = surveyRepository.findById(id);
            if(savedSurvey.isPresent()){
//                Delete from DB
                surveyRepository.deleteById(id);
                response.setMessage("Survey Deleted Successfully");
                response.setStatusCode(HttpStatus.CREATED.value());
            }else{
                response.setMessage("Survey with ID Not Found");
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setEntity("");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }









}
