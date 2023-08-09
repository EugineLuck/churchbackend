package co.ke.emtechhouse.es.MpesaIntergration.Controller;

import co.ke.emtechhouse.es.MpesaIntergration.B2C_Transaction.B2CTransactionAsyncResponse;
import co.ke.emtechhouse.es.MpesaIntergration.B2C_Transaction.B2CTransactionSyncResponse;
import co.ke.emtechhouse.es.MpesaIntergration.B2C_Transaction.InternalB2CTransactionRequest;
import co.ke.emtechhouse.es.MpesaIntergration.C2B_Transaction.C2BRequest;
import co.ke.emtechhouse.es.MpesaIntergration.C2B_Transaction.C2BResponse;
import co.ke.emtechhouse.es.MpesaIntergration.Mpesa_Express.*;
import co.ke.emtechhouse.es.MpesaIntergration.Register_URL.RegisterUrlResponse;
import co.ke.emtechhouse.es.MpesaIntergration.Services.AcknowledgeResponse;
import co.ke.emtechhouse.es.MpesaIntergration.Services.DarajaApi;
import co.ke.emtechhouse.es.MpesaIntergration.Transaction;
import co.ke.emtechhouse.es.MpesaIntergration.TransactionRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("mobile-money")
@Slf4j
public class MpesaController {
    private final DarajaApi darajaApi;
    private final AcknowledgeResponse acknowledgeResponse;
    private final ObjectMapper objectMapper;
    @Autowired
    private TransactionRepo transactionRepo;

    public MpesaController(DarajaApi darajaApi, AcknowledgeResponse acknowledgeResponse, ObjectMapper objectMapper) {
        this.darajaApi = darajaApi;
        this.acknowledgeResponse = acknowledgeResponse;
        this.objectMapper = objectMapper;
    }

    //--------------------Generate Access Token--------------------

//    @GetMapping( path ="/token", produces = "application/json")
//    public ResponseEntity<AccessTokenResponse> getAccessToken(){
//        return ResponseEntity.ok(darajaApi.getAccessToken());
//    }

    //--------------------Register validation/confirmation URLs--------------------

    @PostMapping(path = "/register-url", produces = "application/json")
    public ResponseEntity<RegisterUrlResponse> registerUrl(){
        return ResponseEntity.ok(darajaApi.registerUrl());
    }


    //--------------------C2B Transaction--------------------

    @PostMapping(path = "/c2b-transaction", produces = "application/json")
    public ResponseEntity<C2BResponse> C2BTransaction(@RequestBody C2BRequest c2BRequest){
        log.info("Customer payment underway. Wait for confirmation");
        return ResponseEntity.ok(darajaApi.C2BTransaction(c2BRequest));
    }

    //--------------------B2C Transaction--------------------

    @PostMapping(path = "/b2c-transaction", produces = "application/json")
    public ResponseEntity<B2CTransactionSyncResponse> b2CTransaction(@RequestBody InternalB2CTransactionRequest internalB2CTransactionRequest){
        log.info(".......Initiating B2C Transaction......");
        return ResponseEntity.ok(darajaApi.b2CTransaction(internalB2CTransactionRequest));
    }

    @PostMapping(path = "/b2c-queue-timeout", produces = "application/json")
    public ResponseEntity<AcknowledgeResponse> queueTimeout(@RequestBody Object object){
        return ResponseEntity.ok(acknowledgeResponse);
    }


//    @SneakyThrows
    @PostMapping(path = "/b2c-transaction-result", produces = "application/json")
    public ResponseEntity<AcknowledgeResponse> b2cTransactionAsyncResults(@RequestBody B2CTransactionAsyncResponse b2CTransactionAsyncResponse)
    throws JsonProcessingException{
        log.info(".......B2C Transaction Response......");
        log.info(objectMapper.writeValueAsString(b2CTransactionAsyncResponse));
        return ResponseEntity.ok(acknowledgeResponse);
    }

    //--------------------M-pesa Express--------------------

    @PostMapping(path = "/stk-transaction-request", produces = "application/json")
    public ResponseEntity<StkPushSyncResponse> stkPushTransaction(@RequestBody InternalStkPushRequest internalStkPushRequest){
        log.info("Resonse");
        StkPushSyncResponse response = darajaApi.stkPushTransaction(internalStkPushRequest);
        System.out.println("Response" + response);
        String pushedAmount = internalStkPushRequest.getTransactionAmount();
        String transactionNumber = internalStkPushRequest.getTransactionNumber();


        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @PostMapping(path = "/stk-transaction-result", produces = "application/json")
    public ResponseEntity<AcknowledgeResponse> acknowledgeStkPushResponse(@RequestBody StkPushAsyncResponse stkPushAsyncResponse){
        log.info("====STK Push Async Response====");
//        JSONParser parser = new JSONParser(String.valueOf(stkPushAsyncResponse));
//        log.info(objectMapper.writeValueAsString(parser));
        log.info(objectMapper.writeValueAsString(stkPushAsyncResponse));
        return ResponseEntity.ok(acknowledgeResponse);
    }

    @PostMapping(path = "/stk-transaction-status", produces = "application/json")
    public ResponseEntity<StkPushStatusResponse> stkPushStatus(InternalStkPushStatusRequest internalStkPushStatusRequest){
        return ResponseEntity.ok(darajaApi.stkPushStatus(internalStkPushStatusRequest));
    }

    //--------------------Transaction Status--------------------
    //--------------------Account Balance--------------------

}
