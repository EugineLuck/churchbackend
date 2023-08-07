package co.ke.emtechhouse.es.Parish;

import co.ke.emtechhouse.es.Auth.Members.MembersRepository;
import co.ke.emtechhouse.es.Auth.utils.CONSTANTS;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.Community.Community;
import co.ke.emtechhouse.es.OutStation.OutStation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ParishService {
    @Autowired
    ParishRepository parishRepository;
    private final MembersRepository membersRepository;

    public ParishService(ParishRepository parishRepository, MembersRepository membersRepository) {
        this.parishRepository = parishRepository;
        this.membersRepository = membersRepository;
    }
    public ApiResponse<?> addParish(Parish parish) {
        try {
            ApiResponse response= new ApiResponse<>();
            Parish savedParish = parishRepository.save(parish);
            response.setMessage(HttpStatus.CREATED.getReasonPhrase());
            response.setMessage("PARISH " + parish.getParishName() + " CREATED SUCCESSFULLY " );
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setEntity(savedParish);
            return response;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }
    public ApiResponse<?> getAllparish() {
        try {
            ApiResponse response= new ApiResponse<>();
            List<Parish> parishes= parishRepository.findAll();
            if (parishes.size()>0) {
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(parishes);
            } else {
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }
            return response;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }
public List<Parish> findAll() {
    try {
        List<Parish> parish = parishRepository.findAll();
        return parish;
    } catch (Exception e) {
        log.info("Catched Error {} " + e);
        return null;
    }
}
    public Parish findById(Long id) {
        try {
            ApiResponse response = new ApiResponse<>();
            Optional<Parish> savedParish= parishRepository.findById(id);
            if (savedParish.isPresent()) {
                Parish parish = savedParish.get();
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(parish);
            } else{
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());

            }
            return new Parish();
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }
    public void  delete(Long id) {
        try {
            parishRepository.deleteById(id);
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
        }

    }
}