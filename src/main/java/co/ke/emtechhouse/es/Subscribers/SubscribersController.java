package co.ke.emtechhouse.es.Subscribers;



import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/subscribers")
public class SubscribersController {
    @Autowired
    private SubscribersService subscribersService;
    @PostMapping("/add")
    public ResponseEntity<Object> addSubscription(@RequestBody Subscibers subS) {
        try {
            Subscibers save = subscribersService.saveSubscriber(subS);
            return new ResponseEntity<>(save, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<Object> getAllSubscribers() {
        try {
            List<Subscibers> allsubs= subscribersService.getAll();
            return new ResponseEntity<>(allsubs, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @GetMapping("/get/by/{memberNumber}")
    public ResponseEntity<Object> getByIdAd(@PathVariable String memberNumber) {
        try {
            Subscibers sub = subscribersService.findSubscriptionsByMemberNumber(memberNumber);
            return new ResponseEntity<>(sub, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            subscribersService.delete(id);

        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
        return null;
    }






}
