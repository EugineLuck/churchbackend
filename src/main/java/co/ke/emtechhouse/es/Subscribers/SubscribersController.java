package co.ke.emtechhouse.es.Subscribers;



import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;

import co.ke.emtechhouse.es.NotificationComponent.NotificationDTO;
import co.ke.emtechhouse.es.NotificationComponent.NotificationService;

import co.ke.emtechhouse.es.Subscriptions.DTO;
import co.ke.emtechhouse.es.Subscriptions.Subscriptions;
import co.ke.emtechhouse.es.Subscriptions.SubscriptionsRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/subscribers")
public class SubscribersController {
    @Autowired
    private SubscribersService subscribersService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    SubscriptionsRepo subscriptionsRepo;

    @Autowired
    subscribersSubscriptionsRepo subscribersSubscriptionsRepo;

    @Autowired
            SubscribersRepository subscribersRepository;

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    String nowDate = now.format(formatter);


    @PostMapping("/add")
    public ResponseEntity<Object> addSubscriber(@RequestBody Subscibers subS) {
        ApiResponse response = new ApiResponse();

        try {
            subS.setDateSubscribed(nowDate);



//                    .//fetch all subscriptions
//                    //subscriber.subcriptions

            Subscibers save = subscribersService.saveSubscriber(subS);

            subscribersSubscriptions subscribersSubscriptions = new subscribersSubscriptions();
            subscribersSubscriptions.setSubscriberId(subS.getId());
            subscribersSubscriptions.setSubscriptionId(subS.getSubscriptionItemId());
            subscribersSubscriptionsRepo.save(subscribersSubscriptions);

            response.setMessage("Subscriber Added");
            response.setEntity(save);
            response.setStatusCode(HttpStatus.CREATED.value());

            NotificationDTO notificationsDTO = new NotificationDTO();
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
    public ResponseEntity<?> getAllSubscribers() {
        ApiResponse response = new ApiResponse<>();
        try {
            List<Subscibers> subscribers = subscribersRepository.findAll();

            List allsubs = new ArrayList<>();
            if (subscribers.size() > 0) {
                for (Subscibers subscriber : subscribers) {
                    subscriberDTO dto = new subscriberDTO();
                    dto.setId(subscriber.getId());
                    dto.setPhoneNumber(subscriber.getPhoneNumber());
                    dto.setMemberNumber(subscriber.getMemberNumber());
                    dto.setDateSubscribed(subscriber.getDateSubscribed());
                    dto.setSubscriptionItemId(subscriber.getSubscriptionItemId());

                    List<subscribersSubscriptions> subscriptionsItems = subscribersSubscriptionsRepo.findBySubscriptionId(subscriber.getSubscriptionItemId());
                    List<Subscriptions> subscriptions = new ArrayList<>(); // Create a list to store subscriptions
                    if (subscriptionsItems.size() > 0) {
                        for (subscribersSubscriptions item : subscriptionsItems) {
                            List<Subscriptions> itemSubscriptions = subscriptionsRepo.searchById(item.getSubscriptionId());
                            subscriptions.addAll(itemSubscriptions);
                        }
                    }
                    dto.setSubscriptions(subscriptions); // Set the list of subscriptions in DTO
                    allsubs.add(dto);
                }
            }

            response.setEntity(allsubs);
            response.setMessage("Found");
            response.setStatusCode(HttpStatus.FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred", e);
            return new ResponseEntity<>("Error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/get/by/{memberNumber}")
    public ResponseEntity<Object> getByIdAd(@PathVariable String memberNumber) {
        ApiResponse response = new ApiResponse<>();
        try {
            List allsubs = new ArrayList<>();
            Subscibers subscriptions1 = subscribersService.findSubscriptionsByMemberNumber(memberNumber);
            subscriberDTO dto = new subscriberDTO();
            dto.setId(subscriptions1.getId());
            dto.setPhoneNumber(subscriptions1.getPhoneNumber());
            dto.setMemberNumber(subscriptions1.getMemberNumber());
            dto.setDateSubscribed(subscriptions1.getDateSubscribed());
            dto.setSubscriptionItemId(subscriptions1.getSubscriptionItemId());

            List<subscribersSubscriptions> subscriptionsItems = subscribersSubscriptionsRepo.findBySubscriptionId(subscriptions1.getSubscriptionItemId());
            List<Subscriptions> subscriptions = new ArrayList<>(); // Create a list to store subscriptions
            if (subscriptionsItems.size() > 0) {
                for (subscribersSubscriptions item : subscriptionsItems) {
                    List<Subscriptions> itemSubscriptions = subscriptionsRepo.searchById(item.getSubscriptionId());
                    subscriptions.addAll(itemSubscriptions);
                }
            }
            dto.setSubscriptions(subscriptions);
            allsubs.add(dto);
            response.setEntity(allsubs);
            response.setMessage("Found");
            response.setStatusCode(HttpStatus.FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
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
