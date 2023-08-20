package co.ke.emtechhouse.es.PaymentsMethods;


import co.ke.emtechhouse.es.Advertisement.Advertisement;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/paymentsMethods")
public class PaymentsMethodsController {

    @Autowired
    PaymentsMethodsService paymentsMethodsService;

    public  PaymentsMethodsController(){

    }

    @PostMapping("/add")
    public ResponseEntity<Object> addMethod(@RequestBody PaymentsMethods pmethod) {
        ApiResponse response = new ApiResponse();
        try {
            PaymentsMethods saveMethod = paymentsMethodsService.saveMethod(pmethod);
            response.setMessage("Payment Method Added");
            response.setEntity(saveMethod);
            response.setStatusCode(HttpStatus.CREATED.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error" + e);
            return null;
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<Object> getAllMethods() {
        ApiResponse response = new ApiResponse<>();
        try {
            List<PaymentsMethods> allMethods = paymentsMethodsService.getAll();
            response.setMessage("Payments methods found");
            response.setEntity(allMethods);
            response.setStatusCode(HttpStatus.FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @GetMapping("/get/By/{Id}")
    public ResponseEntity<Object> getMethodById(@PathVariable Long Id) {
        ApiResponse response = new ApiResponse<>();
        try {
            PaymentsMethods savedMethod = paymentsMethodsService.findById(Id);
            response.setMessage("Payment method found");
            response.setEntity(savedMethod);
            response.setStatusCode(HttpStatus.FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error" + e);
            return null;
        }
    }

    @PutMapping("/update/{Id}")
    public ResponseEntity<Object> updateMethod(@PathVariable Long Id, PaymentsMethods pmethod) {
        ApiResponse response = new ApiResponse<>();
        try {
            PaymentsMethods savedMethod = paymentsMethodsService.updateMethod(Id, pmethod);
            response.setMessage("Payment method Updated");
            response.setEntity(savedMethod);
            response.setStatusCode(HttpStatus.FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error" + e);
            return null;
        }
    }


    @DeleteMapping("/delete/{Id}")
    public ResponseEntity<Object> delete(@PathVariable Long Id) {
        ApiResponse response = new ApiResponse<>();
        try {
            response.setMessage("Item "+ Id +" deleted");
            paymentsMethodsService.delete(Id);
            response.setStatusCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error" + e);
            return null;
        }
    }







}
