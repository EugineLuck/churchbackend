package co.ke.emtechhouse.es.Subscribers;



import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;

import co.ke.emtechhouse.es.NotificationComponent.NotificationService;
import co.ke.emtechhouse.es.NotificationComponent.NotificationsDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/subscribers")
public class SubscribersController {
    @Autowired
    private SubscribersService subscribersService;

    @Autowired
    private NotificationService notificationService;

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    String nowDate = now.format(formatter);


    @PostMapping("/add")

    public ResponseEntity<Object> addSubscriber(@RequestBody Subscibers subS) {
        ApiResponse response = new ApiResponse();

        try {
            subS.setDateSubscribed(nowDate);
            Subscibers save = subscribersService.saveSubscriber(subS);

            response.setMessage("Subscriber Added");
            response.setEntity(save);
            response.setStatusCode(HttpStatus.CREATED.value());

            NotificationsDTO notificationsDTO = new NotificationsDTO();
            notificationsDTO.setTitle("New Subscriber");
            notificationsDTO.setMessage("You have a new subscriber\n");
            notificationsDTO.setSubtitle("Subscription Notice");

            notificationService.CreateServiceNotificationforSupscription(notificationsDTO, subS.getSubscriptionItemId());



            return new ResponseEntity<>(response, HttpStatus.OK);
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
