package co.ke.emtechhouse.es.MpesaIntergration;



import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;


import co.ke.emtechhouse.es.DTO.MpesaExpress.MpesaExpressDTO.MpesaexpresscallbackDTO;

import co.ke.emtechhouse.es.MpesaIntergration.Mpesa_Express.StkPushStatusResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.criteria.internal.expression.SubqueryComparisonModifierExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/transaction")
public class  TransactionController {

    @Autowired
    TransactionService transactionService;
    @Autowired
    TransactionRepo transactionRepo;
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


     @GetMapping("/get/by/givingId/{givingId}")
    public ResponseEntity<?> fetchTransactionByGivingId(@PathVariable("givingId") String givingId) {
        try {
            ApiResponse response = transactionService.getByGivingId(Long.valueOf(givingId));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    } @GetMapping("/get/by/memberNumber/{memberNumber}")
    public ResponseEntity<?> fetchTransactionByMemberNumber(@PathVariable("memberNumber") String memberNumber) {
        try {
            ApiResponse response = transactionService.getByMemberNumber(memberNumber);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }



}
