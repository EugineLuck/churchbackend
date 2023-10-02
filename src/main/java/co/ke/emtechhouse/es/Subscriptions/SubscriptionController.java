package co.ke.emtechhouse.es.Subscriptions;


import co.ke.emtechhouse.es.Subscribers.*;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;

import co.ke.emtechhouse.es.MpesaIntergration.Mpesa_Express.InternalStkPushRequest;
import co.ke.emtechhouse.es.MpesaIntergration.Mpesa_Express.StkPushSyncResponse;
import co.ke.emtechhouse.es.MpesaIntergration.Services.DarajaApiImpl;
import co.ke.emtechhouse.es.NotificationComponent.NotificationDTO;
import co.ke.emtechhouse.es.NotificationComponent.NotificationService;
import co.ke.emtechhouse.es.Subscribers.subscribersSubscriptionsRepo;
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
@RequestMapping("/api/v1/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private SubscriptionsRepo subscriptionsRepo;
    @Autowired
    DarajaApiImpl darajaImplementation;

    @Autowired
    NotificationService notificationService;

    @Autowired
    subscribersSubscriptionsRepo subscribersSubscriptionsRepo;

    @Autowired
    SubscribersRepository subscribersRepository;

    @Autowired
    SubscribersService subscribersService;




    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    String nowDate = now.format(formatter);

    @PostMapping("/add")
    public ResponseEntity<Object> addSubscription(@RequestBody Subscriptions subS) {
        ApiResponse response = new ApiResponse();
        try {
            
            Optional<Subscriptions> existing = subscriptionsRepo.findBymemberNumber(subS.getMemberNumber());
            if(existing.isPresent()){

                Subscriptions save = subscriptionService.saveSubscription(subS);

                NotificationDTO notificationsDTO = new NotificationDTO();
                notificationsDTO.setTitle("New Post");
                notificationsDTO.setMessage("A church member has joined our Ads/Mentor Program. Visit career page to explore.\n");
                notificationsDTO.setSubtitle("Subscription Notice");
                notificationService.CreateServiceNotificationAll(notificationsDTO);

                response.setMessage("Subscription Added");
                response.setEntity(save);
                response.setStatusCode(HttpStatus.CREATED.value());


            }else{

                subS.setDateCreated(nowDate);
                InternalStkPushRequest data = new InternalStkPushRequest();
                data.setMemberNumber(subS.getMemberNumber());
                data.setTransactionAmount(subS.getCharges());
                data.setTransactionNumber(subS.getPhoneNumber());
                data.setTransactionType("subscription");



                StkPushSyncResponse response1 = darajaImplementation.stkPushTransaction(data);
                subS.setDateCreated(String.valueOf(new Date()));

                if(response1.getResultCode().equals("0")){
                    Subscriptions save = subscriptionService.saveSubscription(subS);
                    response.setMessage("Subscription Added");
                    response.setEntity(save);
                    response.setStatusCode(HttpStatus.CREATED.value());

                    NotificationDTO notificationsDTO = new NotificationDTO();
                    notificationsDTO.setTitle("New Post");
                    notificationsDTO.setMessage("A church member has joined our Ads/Mentor Program. Visit career page to explore.\n");
                    notificationsDTO.setSubtitle("Subscription Notice");
                    notificationService.CreateServiceNotificationAll(notificationsDTO);

                }else{
                    response.setMessage(response1.getResultDesc());
                    response.setStatusCode(HttpStatus.NOT_FOUND.value());



                }

            }





            return new ResponseEntity<>(response, HttpStatus.OK);


        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }


    @GetMapping("/get/all")
    public ResponseEntity<?> getAllSubscriptions() {
        ApiResponse response = new ApiResponse<>();
        try {
            List<Subscriptions> subscribers = subscriptionsRepo.findAll();

            List allsubs = new ArrayList<>();
            if (subscribers.size() > 0) {
                for (Subscriptions subscriptions1 : subscribers) {
                    DTO dto = new DTO();
                    dto.setId(subscriptions1.getId());
                    dto.setPhoneNumber(subscriptions1.getPhoneNumber());
                    dto.setMemberNumber(subscriptions1.getMemberNumber());
                    dto.setFullName(subscriptions1.getFullName());
                    dto.setSubscriptionType(subscriptions1.getSubscriptionType());
                    dto.setCharges(subscriptions1.getCharges());
                    dto.setDescriptionInfo(subscriptions1.getDescriptionInfo());
                    dto.setBanner(subscriptions1.getBanner());
                    dto.setDateCreated(subscriptions1.getDateCreated());

                    List<subscribersSubscriptions> subscribersItems = subscribersSubscriptionsRepo.findBySubscriptionId(subscriptions1.getId());
                    if (subscribersItems.size() > 0) {
                        List<Subscibers> subs = new ArrayList<>(); // Create a list to store subscribers
                        for (subscribersSubscriptions item : subscribersItems) {
                            List<Subscibers> itemSubs = subscribersRepository.searchByItemId(item.getSubscriberId());
                            subs.addAll(itemSubs); // Add the subscribers to the list
                        }
                        dto.setSubscribers(subs); // Set the list of subscribers in DTO
                    }
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
    public ApiResponse getSubscriptionByMemberNumber(@PathVariable String memberNumber) {
        ApiResponse response = new ApiResponse<>();

        List subscription = new ArrayList<>();

        Optional<Subscriptions> subscr = subscriptionsRepo.findBymemberNumber(memberNumber);
        if (subscr.isPresent()) {
            Subscriptions subscriptions1 = subscr.get();
            DTO dto = new DTO();
            dto.setFullName(subscriptions1.getFullName());
            dto.setId(subscriptions1.getId());
            dto.setPhoneNumber(subscriptions1.getPhoneNumber());
            dto.setMemberNumber(subscriptions1.getMemberNumber());
            dto.setSubscriptionType(subscriptions1.getSubscriptionType());
            dto.setCharges(subscriptions1.getCharges());
            dto.setDescriptionInfo(subscriptions1.getDescriptionInfo());
            dto.setBanner(subscriptions1.getBanner());
            dto.setDateCreated(subscriptions1.getDateCreated());

            List<subscribersSubscriptions> subscribersItems = subscribersSubscriptionsRepo.findBySubscriptionId(subscriptions1.getId());
            if (subscribersItems.size() > 0) {
                List<Subscibers> subs = new ArrayList<>(); // Create a list to store subscribers
                for (subscribersSubscriptions item : subscribersItems) {
                    List<Subscibers> itemSubs = subscribersRepository.searchByItemId(item.getSubscriberId());
                    subs.addAll(itemSubs); // Add the subscribers to the list
                }
                dto.setSubscribers(subs); // Set the list of subscribers in DTO
            }

            subscription.add(dto);
            response.setMessage(HttpStatus.FOUND.getReasonPhrase());
            response.setStatusCode(HttpStatus.FOUND.value());
            response.setEntity(subscription);
            return response;
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
