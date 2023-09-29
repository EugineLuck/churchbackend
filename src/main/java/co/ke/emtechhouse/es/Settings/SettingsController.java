package co.ke.emtechhouse.es.Settings;

import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/v1/settings")
public class SettingsController {

    @Autowired
    SettingsRepository settingsRepository;

    public  SettingsController(){

    }

    @PostMapping("/set")
    public ResponseEntity<Object> setSettings(@RequestBody Settings settings) {
        ApiResponse response = new ApiResponse<>();
        try {

            Optional<Settings> existing = settingsRepository.findByMetaField(settings.getMetaField());
            if(existing.isEmpty()){
                Settings savedSettings = settingsRepository.save(settings);
            }else{
                Settings existingSetting = existing.get();
                existingSetting.setMetaValue(settings.getMetaValue());
                Settings save = settingsRepository.save(existingSetting);
            }
            response.setMessage("Settings Saved");
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error "+ e);
            return null;
        }
    }

    @GetMapping("/")
    public ResponseEntity<Object> loadSettings() {
        ApiResponse response = new ApiResponse<>();
        try {
            List<Settings> savedSettings = settingsRepository.findAll();
            response.setEntity(savedSettings);
            response.setMessage("Found");
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error "+ e);
            return null;
        }
    }





}
