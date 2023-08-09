package co.ke.emtechhouse.es.MpesaIntergration;

import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class FailedController {

    @Autowired
    FailedTransService failedTransService;
    @RequestMapping(path = "api/v1/FailedTransaction")




    @GetMapping("/get/all")
    public ResponseEntity<?> fetchAll() {
        try {
            ApiResponse response = failedTransService.getAllTransactions();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    @GetMapping("/get/allFailedTransactions")
    public ResponseEntity<?> getAll() {
        try {
            ApiResponse response = failedTransService.getAllFailedTransactions();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @GetMapping("/get/by/Phone/{phoneNumber}")
    public ResponseEntity<?> fetchTransactionsByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        try {
            ApiResponse response = failedTransService.getTransactionsByPhoneNumber(phoneNumber);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @GetMapping("/get/by/MemberNumber/{memberNumber}")
    public ResponseEntity<?> fetchTransactionsByMemberNumber(@PathVariable("memberNumber") String memberNumber) {
        try {
            ApiResponse response = failedTransService.getTransactionsByMemberNumber(memberNumber);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }


    @GetMapping("/get/by/giving/{givingId}")
    public ResponseEntity<?> getTransactionsByGivingId(@PathVariable("givingId") Long givingId)
    {
        try {
            ApiResponse apiResponse = failedTransService.getTransactionsByGivingId(givingId);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Caught Error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

}

