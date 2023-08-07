package co.ke.emtechhouse.es.HelpComponent.ContactInfo;

import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/help/contactInfo")
public class ContactController {
    @Autowired
    ContactService contactService;
    @GetMapping("/get")
    public ResponseEntity<?> getContactInfo() {
        try {
            ApiResponse response = contactService.getContactInfo();
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;

        }
    }
//Changes
    @PostMapping("/add")
    public ResponseEntity<?> addContactInfo(@RequestBody Contact contactInfo) {
        try {
            ApiResponse response = contactService.addContactInfo(contactInfo);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch(Exception e){
            log.info("Catched Error {} " + e);
            return null;

        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteContactInfo(){
        try {
            ApiResponse response = contactService.deleteContactInfo();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateContactInfo(@RequestBody Contact newContactInfo){
        try {
            ApiResponse response = contactService.updateContactInfo(newContactInfo);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch(Exception e){
            log.info("Catched Error {}" + e);
            return null;
        }
    }
}

