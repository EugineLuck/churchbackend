package co.ke.emtechhouse.es.MpesaIntergration;



import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;


import co.ke.emtechhouse.es.DTO.MpesaExpress.MpesaExpressDTO.MpesaexpresscallbackDTO;

import co.ke.emtechhouse.es.MpesaIntergration.Mpesa_Express.StkPushStatusResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/transaction")
public class  TransactionController {

    @Autowired
    TransactionService transactionService;
    @RequestMapping(path = "api/v1/transaction")



        @PostMapping("/add")
        public ResponseEntity<?> newTransaction(@RequestBody Transaction transaction) {
            try{
                ApiResponse response = transactionService.enter2(transaction);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }catch (Exception e){
                log.info("Catched Error {} " + e);
                return null;
            }
        }
    @PostMapping("/callback")
    public ResponseEntity<?> mpesaExpressCallback(@RequestBody StkPushStatusResponse stkPushStatusResponse) {
        try{
            transactionService.callback(stkPushStatusResponse);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @GetMapping("/get/all")
    public ResponseEntity<?> fetchAll() {
        try {
            ApiResponse response = transactionService.getAllTransactions();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @GetMapping("/get/successfullyTransactions")
    public ResponseEntity<?> getAll() {
        try {
            ApiResponse response = transactionService.getMemberTransactions();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @GetMapping("/get/by/Phone/{phoneNumber}")
    public ResponseEntity<?> fetchTransactionsByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        try {
            ApiResponse response = transactionService.getTransactionsByPhoneNumber(phoneNumber);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @GetMapping("/get/by/MemberNumber/{memberNumber}")
    public ResponseEntity<?> fetchTransactionsByMemberNumber(@PathVariable("memberNumber") String memberNumber) {
        try {
            ApiResponse response = transactionService.getTransactionsByMemberNumber(memberNumber);
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
            ApiResponse apiResponse = transactionService.getTransactionsByGivingId(givingId);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Caught Error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

}
