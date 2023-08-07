package co.ke.emtechhouse.es.Mentors;


import co.ke.emtechhouse.es.Advertisement.AdsRepo;
import co.ke.emtechhouse.es.Advertisement.Advertisement;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MentorsService {

    @Autowired
    private MentorsRepo mentorsRepo;

    public Mentors saveMentor(Mentors mentor){
        try {
            Mentors saveMentor = mentorsRepo.save(mentor);
            return saveMentor;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }


    public List<Mentors> getAll() {
        try {
            List<Mentors> mentors = mentorsRepo.findAll();
            return mentors;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }

    public Mentors findById(Long id) {
        try {
            Mentors mentor = mentorsRepo.findById(id).get();
            return mentor;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }

//Update

//    public Mentors updateMentor(Long id) {
//        try {
//            Mentors mentor = mentorsRepo.findById(id).get();
//            return mentor;
//        }catch (Exception e) {
//            log.info("Catched Error {} " + e);
//            return null;
//        }
//
//    }







    //    Delete
    public void  delete(Long id) {
        try {
            mentorsRepo.deleteById(id);
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
        }

    }









}

