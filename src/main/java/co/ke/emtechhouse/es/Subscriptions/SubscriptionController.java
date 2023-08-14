package co.ke.emtechhouse.es.Subscriptions;


import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("/api/v1/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    SubscriptionsRepo subscriptionsRepo;
    @PostMapping("/add")
    public ResponseEntity<Object> addSubscription(@RequestBody Subscriptions subS) {
        ApiResponse response = new ApiResponse();
        try {
            Subscriptions save = subscriptionService.saveSubscription(subS);
            response.setMessage("Subscription added Successful");
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setEntity(save);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllSubscriptions() {
        try{
            ApiResponse response = subscriptionService.getAll();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    @GetMapping("/get/by/{memberNumber}")
    public ApiResponse  getByIdAd(@PathVariable String memberNumber) {
        ApiResponse response = new ApiResponse<>();
        Optional<Subscriptions> subs  = subscriptionsRepo.findBymemberNumber(memberNumber);
        if (subs.isPresent()) {
            Subscriptions subscription = subs.get();
            response.setMessage(HttpStatus.FOUND.getReasonPhrase());
            response.setStatusCode(HttpStatus.FOUND.value());
            response.setEntity(subscription);
            return response;
//            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Subscription with MemberNumber not found");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return response;
        }
    }



    @DeleteMapping("/delete/id")
    public ResponseEntity<Object> delete(Long id) {
        try {
            subscriptionService.delete(id);

        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
        return null;
    }







}
