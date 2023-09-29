package co.ke.emtechhouse.es.Settings;

import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/settings")
public class SettingsController {

    @Autowired
    SettingsRepository settingsRepository;

    public  SettingsController(){

    }

    @PostMapping("/set")
    public ResponseEntity<Object> setSettings(@RequestBody SettingsDTO settings) {
        ApiResponse response = new ApiResponse<>();
        try {
            // Settings savedSettings = settingsRepository.save(settings);
            response.setEntity(settings.getSettings());
            response.setMessage("Settings Saved");
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error "+ e);
            return null;
        }
    }

//    @PutMapping("/update")
//    public ResponseEntity<Object> updateSettings(@RequestBody Settings settings) {
//        ApiResponse response = new ApiResponse<>();
//        try {
//            Settings savedSettings = settingsRepository.save(settings);
//            response.setEntity(savedSettings);
//            response.setMessage("Settings Saved");
//            response.setStatusCode(200);
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            log.info("Error" + e);
//            return null;
//        }
//    }





}
