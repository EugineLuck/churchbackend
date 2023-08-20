package co.ke.emtechhouse.es.NotificationComponent;

import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;


import co.ke.emtechhouse.es.Family.FamilyMember.FamilyMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Autowired
    FamilyMemberService familyMemberService;

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllNotifications() {
        try {
            ApiResponse response = notificationService.getAllNotifications();
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            log.info("Error {}" + e);
            return null;
        }
    }

    @PostMapping("/add/notification/{groupId}")
    public ResponseEntity<?> createNotification(@PathVariable("groupId") Long groupId,
                                                @RequestBody Notification notification) {
        try {
            ApiResponse apiResponse = notificationService.CreateServiceNotification(groupId, notification);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);

        } catch (Exception e) {
            log.info("Error:" + e);
            return null;
        }
    }

    @PostMapping("/add/notification/all")
    public ResponseEntity<?> createNotificationAll(@RequestBody NotificationDTO notification) {
        try {
            ApiResponse apiResponse = notificationService.CreateServiceNotificationAll(notification);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);

        } catch (Exception e) {
            log.info("Error:" + e);
            return null;
        }
    }

    


    @GetMapping("/get/notification/by/memberNumber/{memberNumber}")
    public ResponseEntity<?> getNotificationsByMemberNumber(@PathVariable("memberNumber") String memberNumber) {
        try {
            ApiResponse response = notificationService.getNotificationByMemberNumber(memberNumber);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error{}" + e);
            return null;
        }
    }

    @GetMapping("/get/by/id/{notificationId}")
    public ResponseEntity<?> getNotificationById(@PathVariable("notificationId") Long notificationId) {
        try {
            ApiResponse apiResponse = notificationService.getNotification(notificationId);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error {}" + e);
            return null;
        }
    }

    //    @PostMapping("/add")
//    public  ResponseEntity<?> sendPushMessage(){
//    try {
//        ApiResponse apiResponse = notificationService.sendSystemNotification();
//        return  new ResponseEntity<>(apiResponse, HttpStatus.OK);
//
//    }catch (Exception e){
//        log.info("Error:" + e);
//        return  null;
//    }
//    }
//    @PostMapping("/update/status/{notificationId}/{notificationStatus}")
//    public ResponseEntity<?> updateNotification(@PathVariable("notificationId") Long notificationId, @PathVariable("notificationStatus") NotificationStatus notificationStatus) {
//        try {
//            ApiResponse apiResponse = notificationService.updateNotificationStatus(notificationId, notificationStatus);
//            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
//
//        } catch (Exception e) {
//            log.info("Error" + e);
//            return null;
//        }
//    }




}
