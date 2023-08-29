package co.ke.emtechhouse.es.Subscriptions;


import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;

import co.ke.emtechhouse.es.MpesaIntergration.Mpesa_Express.InternalStkPushRequest;
import co.ke.emtechhouse.es.MpesaIntergration.Mpesa_Express.StkPushSyncResponse;
import co.ke.emtechhouse.es.MpesaIntergration.Services.DarajaApiImpl;
import co.ke.emtechhouse.es.NotificationComponent.NotificationDTO;
import co.ke.emtechhouse.es.NotificationComponent.NotificationService;
import co.ke.emtechhouse.es.Subscribers.Subscibers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;


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




    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    String nowDate = now.format(formatter);

    @PostMapping("/add")
    public ResponseEntity<Object> addSubscription(@RequestBody Subscriptions subS) {
        ApiResponse response = new ApiResponse();
        try {
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

                NotificationDTO notificationsDTO = new NotificationDTO();
                notificationsDTO.setTitle("New Post");
                notificationsDTO.setMessage("A church member has joined our Ads/Mentor Program. Visit career page to explore.\n");
                notificationsDTO.setSubtitle("Subscription Notice");
                notificationService.CreateServiceNotificationAll(notificationsDTO);

            }



            return new ResponseEntity<>(response, HttpStatus.OK);


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
    public ApiResponse getSubscriptionByMemberNumber(@PathVariable String memberNumber) {
        ApiResponse response = new ApiResponse<>();
        Optional<Subscriptions> subscr = subscriptionsRepo.findBymemberNumber(memberNumber);
        if (subscr.isPresent()) {
            Subscriptions subscription = subscr.get();
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
