package co.ke.emtechhouse.es.GivingLevels;


import co.ke.emtechhouse.es.Advertisement.Advertisement;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/levels")
public class GivingLevelController {

    @Autowired
    GivingLevelRepo givingLevelRepo;

    @GetMapping("/all")
    public ResponseEntity<Object> fetchAll() {
        try {
            List<GivingLevel> allAdverts= givingLevelRepo.findAll();
            return new ResponseEntity<>(allAdverts, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }
}
