package co.ke.emtechhouse.es.Subscriptions;


import co.ke.emtechhouse.es.Advertisement.Advertisement;
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
        try {
            Subscriptions save = subscriptionService.saveSubscription(subS);
            return new ResponseEntity<>(save, HttpStatus.OK);
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
