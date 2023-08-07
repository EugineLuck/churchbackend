package co.ke.emtechhouse.es.BankCard;

import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/bank/card")
public class BankCardController {

    @Autowired
    BankCardService bankCardService;

    @PostMapping("/add")
    public ResponseEntity<?> newBankCard(@RequestBody BankCard bankCard) {
        try{
            ApiResponse response = bankCardService.addBankCard(bankCard);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @GetMapping("/get/all")
    public ResponseEntity<?> fetchAll() {
        try{
            ApiResponse response = bankCardService.getAllBankCards();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @GetMapping("/get/by/id/{bankCardId}")
    public ResponseEntity<?> fetchBankCardById(@PathVariable("bankCardId") Long bankCardId) {
        try{
            ApiResponse response = bankCardService.getBankCardById(bankCardId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateBankCard(@RequestBody BankCard bankCard) {
        try{
            ApiResponse response =bankCardService.updateBankCard(bankCard);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    @GetMapping("/get/by/usersId/{usersId}")
    public ResponseEntity<?> fetchBankCardsByUsersId(@PathVariable("usersId") Long userId) {
        try {
            ApiResponse response = bankCardService.getBankCardsByUsersId(userId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @PutMapping("/delete/temp/{bankCardId}")
    public ResponseEntity<?> deleteBankCard(@PathVariable("bankCardId") Long bankCardId) {
        try{
            ApiResponse response = bankCardService.tempDeleteBankCard(bankCardId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
}
