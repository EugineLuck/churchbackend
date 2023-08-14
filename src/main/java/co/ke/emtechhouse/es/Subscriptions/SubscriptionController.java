package co.ke.emtechhouse.es.Subscriptions;


import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/v1/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;
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
    public ResponseEntity<Object> getAllSubscriptions() {


        try {
            List<Subscriptions> allsubs= subscriptionService.getAll();
            return new ResponseEntity<>(allsubs, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @GetMapping("/get/by/{memberNumber}")
    public ResponseEntity<Object> getByIdAd(@PathVariable String memberNumber) {
        try {
            Subscriptions sub = subscriptionService.findByMemberNumbers(memberNumber);
            return new ResponseEntity<>(sub, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
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
