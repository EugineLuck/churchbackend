package co.ke.emtechhouse.es.BankComponent;

import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/Bank")
public class BankController {
    @Autowired
    BankService bankService;

    @PostMapping("/add")
    public ResponseEntity<?> newBank(@RequestBody Bank bank) {
        try{
            ApiResponse response = bankService.addBank(bank);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @GetMapping("/get/all")
    public ResponseEntity<?> fetchAll() {
        try{
            ApiResponse response = bankService.getAllBanks();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @GetMapping("/get/by/id/{bankId}")
    public ResponseEntity<?> fetchBankById(@PathVariable("bankId") Long bankId) {
        try{
            ApiResponse response = bankService.getBankById(bankId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateBank(@RequestBody Bank bank) {
        try{
            ApiResponse response =bankService.updateBank(bank);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @PutMapping("/delete/temp/{bankId}")
    public ResponseEntity<?> deleteBank(@PathVariable("bankId") Long bankId) {
        try{
            ApiResponse response = bankService.tempDeleteBank(bankId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
}

