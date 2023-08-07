package co.ke.emtechhouse.es.MpesaIntergration;



import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;


import co.ke.emtechhouse.es.DTO.MpesaExpress.MpesaExpressDTO.MpesaexpresscallbackDTO;

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
    public ResponseEntity<?> mpesaExpressCallback(@RequestBody MpesaexpresscallbackDTO mpesaexpresscallbackDTO) {
        try{
            transactionService.callback(mpesaexpresscallbackDTO);
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
    @GetMapping("/get/by/GivingId/{givingId}")
    public ResponseEntity<?> fetchTransactionsBySavingScheduleId(@PathVariable("givingId") Long givingId) {
        try {
            ApiResponse response = transactionService.getTransactionsByGivingId(givingId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }


    @GetMapping("/get/by/giving/{givingId}/members/{memberNumber}/status/{status}")
    public ResponseEntity<?> getTransactionsByGoalIdAndStatus(

            @PathVariable Long givingId,
            @PathVariable String memberNumber,
            @PathVariable ("status")String status) {
        try {
            ApiResponse apiResponse = transactionService.getTransactionsByMemberNumberAndGivingIdAndStatus(memberNumber, givingId, status);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Caught Error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

}
