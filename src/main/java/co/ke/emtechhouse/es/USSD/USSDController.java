package co.ke.emtechhouse.es.USSD;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/emtech/api/v1/ussd/")
@Slf4j
public class USSDController {
    @Autowired
    private USSDService ussdService;
    @RequestMapping("/callback")
    public ResponseEntity<?> getUSSDCallback(@RequestParam("SESSIONID") String sessionId, @RequestParam("USSDCODE") String ussdCode, @RequestParam("MSISDN") String msisDn, @RequestParam("INPUT") String input, @RequestParam("NETWORK") String network) {
        log.info("Session Id - "+sessionId);
        log.info("USSD Code - "+ussdCode);
        log.info("Input - "+input);
        log.info("Network - "+network);
        String response = "";

        return ussdService.processUSSDRequests(msisDn, input, ussdCode, sessionId);
    }

}
