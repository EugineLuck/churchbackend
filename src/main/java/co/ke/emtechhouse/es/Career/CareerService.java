package co.ke.emtechhouse.es.Career;


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
public class CareerService {
    @Autowired
    private CareerRepository CareerRepository;


    public Career saveCareer(Career career){
        try {
            Career savedCareer = CareerRepository.save(career);
            return savedCareer;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    public List<Career> getAll() {
        try {
            List<Career> careers= CareerRepository.findAll();
            return careers;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }

    public Career findById(Long id) {
        try {
            Career savedCareer= CareerRepository.findById(id).get();
            return savedCareer;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }

    public ApiResponse<Advertisement> updateCareer(Career career, Long Id) {
        try {
            ApiResponse response = new ApiResponse<>();
            Optional<Career> car = CareerRepository.findById(Id);
            if (car.isPresent()) {

                Career existingCareer = car.get();
                existingCareer.setUserOccupation(career.getUserOccupation());
                existingCareer.setPhoneNumber(career.getPhoneNumber());
                existingCareer.setCareerDescription(career.getCareerDescription());
                existingCareer.setUserName(career.getUserName());

                Career SavedAd = CareerRepository.save(existingCareer);
                response.setMessage("Career  updated successfully");
                response.setStatusCode(HttpStatus.OK.value());
                response.setEntity(SavedAd);
                return response;
            } else {
                response.setMessage("Career Not found");
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }
            return response;
        } catch (Exception e) {
            log.info("Catched Error {}" + e);
            return null;
        }

    }


    public void  delete(Long id) {
        try {
            CareerRepository.deleteById(id);
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
        }

    }








}
