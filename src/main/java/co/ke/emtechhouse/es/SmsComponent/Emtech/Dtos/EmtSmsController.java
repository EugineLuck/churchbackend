package co.ke.emtechhouse.es.SmsComponent.Emtech.Dtos;

import co.ke.emtechhouse.es.SmsComponent.Emtech.Dtos.Dtos.SmsDto;
import co.ke.emtechhouse.es.utils.Responses.EntityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("emt-sms")
@RestController
public class EmtSmsController {
    @Autowired
    private EmtSmsService emtSmsService;

    @PostMapping("send/sms")
    public ResponseEntity<?> sendSms(@RequestBody SmsDto smsDto) {
        try{
            EntityResponse res= emtSmsService.sendSms(smsDto);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }catch (Exception e) {
            log.info("Caught Error {}" + e.getMessage());
            return null;
        }
    }
}
