package co.ke.emtechhouse.es.Auth.MailService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@Slf4j
public class MailController {
    @Autowired
    private MailService mailService;


    @PostMapping(path = "/send/email")
    public ResponseEntity<?> sendEmail(@RequestBody MailDto mailDto) {
        try {
            mailService.sendNotification( mailDto.getTo(),  mailDto.getMessage(),  mailDto.getSubject());
            return ResponseEntity.ok("mail sent");
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }
}

