package co.ke.emtechhouse.es.NotificationComponent;

import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Auth.Members.MembersRepository;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.Community.Community;
import co.ke.emtechhouse.es.Community.CommunityDetails;
import co.ke.emtechhouse.es.Family.Family;
import co.ke.emtechhouse.es.Family.FamilyMember.FamilyMember;
import co.ke.emtechhouse.es.Family.FamilyRepository;
import co.ke.emtechhouse.es.GoalComponent.GoalRepo;
import co.ke.emtechhouse.es.Groups.GroupMemberComponent.GroupMember;
import co.ke.emtechhouse.es.Groups.GroupMemberComponent.GroupMemberRepo;
import co.ke.emtechhouse.es.Groups.Groups;
import co.ke.emtechhouse.es.Groups.GroupsRepo;
import co.ke.emtechhouse.es.Groups.GroupsService;
import co.ke.emtechhouse.es.NotificationComponent.TokenComponent.Token;
import co.ke.emtechhouse.es.NotificationComponent.TokenComponent.TokenRepo;
import co.ke.emtechhouse.es.NotificationComponent.TokenNotifications.TokenNotificationKey;
import co.ke.emtechhouse.es.NotificationComponent.TokenNotifications.TokenNotifications;
import co.ke.emtechhouse.es.OutStation.OutStation;
import co.ke.emtechhouse.es.SmsComponent.Emtech.Dtos.Dtos.SmsDto;
import co.ke.emtechhouse.es.Subscriptions.Subscriptions;
import co.ke.emtechhouse.es.Subscriptions.SubscriptionsRepo;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class NotificationService {
    @Autowired
    NotificationRepo notificationRepo;
    @Autowired
    TokenRepo tokenRepo;
    @Autowired
    MembersRepository membersRepository;
    @Autowired
    GoalRepo goalRepo;

    @Autowired
    GroupsService groupsService;


    @Autowired
    SubscriptionsRepo subscriptionsRepo;

    @Autowired
    GroupsRepo groupsRepo;
    @Autowired
    GroupMemberRepo groupMemberRepo;
    @Autowired
    FamilyRepository familyRepository;
    @Value("${spring.firebase.fcm_api}")
    private String FCM_API;
    @Value("${spring.firebase.server_key}")
    private String SERVER_KEY;



    public ApiResponse getAllNotifications() {
        try {
            ApiResponse apiResponse = new ApiResponse();
            List<Notification> notificationList = notificationRepo.findAll();
            if (notificationList.size() > 0) {
                apiResponse.setMessage(HttpStatus.FOUND.getReasonPhrase());
                apiResponse.setStatusCode(HttpStatus.FOUND.value());
                apiResponse.setEntity(notificationList);
            } else {
                apiResponse.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            }
            return apiResponse;
        } catch (Exception e) {
            log.info("Error {}" + e);
            return null;
        }
    }

    public ApiResponse<Notification> getNotification(Long id) {
        try {
            ApiResponse apiResponse = new ApiResponse();
            Optional<Notification> notificationOptional = notificationRepo.findById(id);
            if (notificationOptional.isPresent()) {
                Notification notification = notificationOptional.get();
                apiResponse.setMessage(HttpStatus.FOUND.getReasonPhrase());
                apiResponse.setStatusCode(HttpStatus.FOUND.value());
                apiResponse.setEntity(notification);
                return apiResponse;
            } else {
                apiResponse.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
                return apiResponse;
            }
        } catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }


    public ApiResponse CreateServiceNotificationAll(NotificationDTO notificationsDTO ) {

        try {
            ApiResponse apiResponse = new ApiResponse();
            List<Members> members  = membersRepository.findAll();
            if(members.isEmpty()){
                return null;
            }

            for(Members member1 : members){
                String memberNumber = member1.getMemberNumber();
                Optional<Token> tokenOptional = tokenRepo.findByMemberNumber(member1.getMemberNumber());
                System.out.println("Checking here......" + memberNumber);
                if (tokenOptional.isPresent()) {
                    Notification notification1 = new Notification();
                    notification1.setTitle(notificationsDTO.getTitle());
                    notification1.setMessage(notificationsDTO.getMessage());
                    notification1.setSubtitle(notificationsDTO.getSubtitle());

                    Notification savedNotification = notificationRepo.save(notification1);
                    System.out.println("Cheking token if its getting "+ tokenOptional.get());

                    saveTokensInNotification(savedNotification, tokenOptional.get());
                    apiResponse.setMessage(HttpStatus.FOUND.getReasonPhrase());
                    apiResponse.setStatusCode(HttpStatus.FOUND.value());
                    apiResponse.setEntity(savedNotification);


                } else {
                    log.info("Member's token  does not exist");
                }
            }
            return apiResponse;
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    public ApiResponse CreateServiceNotificationforSupscription(NotificationDTO notificationsDTO, Long itemId ) {
        try {
            ApiResponse apiResponse = new ApiResponse();
            Optional<Subscriptions> subscriptionHolder  = subscriptionsRepo.findById(itemId);
            if(subscriptionHolder.isPresent()){
                String memberNumber = subscriptionHolder.get().getMemberNumber();
                Optional<Token> tokenOptional = tokenRepo.findByMemberNumber(subscriptionHolder.get().getMemberNumber());
                System.out.println("Checking here......" + memberNumber);
//
                if (tokenOptional.isPresent()) {

                    Notification notification1 = new Notification();
                    notification1.setTitle(notificationsDTO.getTitle());
                    notification1.setMessage(notificationsDTO.getMessage());
                    notification1.setSubtitle(notificationsDTO.getSubtitle());

                    Notification savedNotification = notificationRepo.save(notification1);
                    saveTokensInNotification(savedNotification, tokenOptional.get());
                    apiResponse.setMessage(HttpStatus.FOUND.getReasonPhrase());
                    apiResponse.setStatusCode(HttpStatus.FOUND.value());
                    apiResponse.setEntity(savedNotification);
                } else {
                    log.info("Member's token  does not exist");
                }
            }
            return apiResponse;
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }





//Groups
    public ApiResponse CreateServiceNotificationAllSelectedGroups(NotificationDTO notificationDTO, Groups group) {
        try {
            ApiResponse apiResponse = new ApiResponse();

            if(!(group == null)){
                List<Object[]> membersData = groupsRepo.getGroupMembers(group.getId());
                List<Members> allMembers = new ArrayList<>();
                for (Object[] data : membersData) {
                    System.out.println("Member Number......."+ (String) data[0]);
                    Optional<Token> tokenOptional = tokenRepo.findByMemberNumber((String) data[0]);
                        if (tokenOptional.isPresent()) {
                            Notification notification1 = new Notification();
                            notification1.setTitle(notificationDTO.getTitle());
                            notification1.setMessage(notificationDTO.getMessage());
                            notification1.setSubtitle(notificationDTO.getSubtitle());
                            Notification savedNotification = notificationRepo.save(notification1);
                            System.out.println("Checking token"+ tokenOptional.get().getDeviceToken());
                            saveTokensInNotification(savedNotification, tokenOptional.get());
                            apiResponse.setMessage(HttpStatus.FOUND.getReasonPhrase());
                            apiResponse.setStatusCode(HttpStatus.FOUND.value());
                            apiResponse.setEntity(savedNotification);
                        } else {
                            log.info("Member's token  does not exist");
                        }

                }
            }
            return apiResponse;
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    //Families
    public ApiResponse CreateServiceNotificationAllSelectedFamilies(NotificationDTO notificationDTO, Family church) {
        try {
            ApiResponse apiResponse = new ApiResponse();
            if(!(church == null)){
                List<Members> chucrchMembers = church.getMembers();
                for (Members familyMemberDetails : chucrchMembers) {
                    System.out.println("Member Number......."+ familyMemberDetails.getMemberNumber());
                    Optional<Token> tokenOptional = tokenRepo.findByMemberNumber(familyMemberDetails.getMemberNumber());
                    if (tokenOptional.isPresent()) {
                        Notification notification1 = new Notification();
                        notification1.setTitle(notificationDTO.getTitle());
                        notification1.setMessage(notificationDTO.getMessage());
                        notification1.setSubtitle(notificationDTO.getSubtitle());
                        Notification savedNotification = notificationRepo.save(notification1);
                        System.out.println("Checking token"+ tokenOptional.get().getDeviceToken());
                        saveTokensInNotification(savedNotification, tokenOptional.get());
                        apiResponse.setMessage(HttpStatus.FOUND.getReasonPhrase());
                        apiResponse.setStatusCode(HttpStatus.FOUND.value());
                        apiResponse.setEntity(savedNotification);
                    } else {
                        log.info("Member's token  does not exist");
                    }

                }
            }
            return apiResponse;
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

//Churches | Outstation
    public ApiResponse CreateServiceNotificationAllSelectedOutstation(NotificationDTO notificationDTO, OutStation family) {
        try {
            ApiResponse apiResponse = new ApiResponse();
            if(!(family == null)){
                List<Members> familyMember = family.getMembers();
                for (Members familyMemberDetails : familyMember) {
                    System.out.println("Member Number......."+ familyMemberDetails.getMemberNumber());
                    Optional<Token> tokenOptional = tokenRepo.findByMemberNumber(familyMemberDetails.getMemberNumber());
                    if (tokenOptional.isPresent()) {
                        Notification notification1 = new Notification();
                        notification1.setTitle(notificationDTO.getTitle());
                        notification1.setMessage(notificationDTO.getMessage());
                        notification1.setSubtitle(notificationDTO.getSubtitle());
                        Notification savedNotification = notificationRepo.save(notification1);
                        System.out.println("Checking token"+ tokenOptional.get().getDeviceToken());
                        saveTokensInNotification(savedNotification, tokenOptional.get());
                        apiResponse.setMessage(HttpStatus.FOUND.getReasonPhrase());
                        apiResponse.setStatusCode(HttpStatus.FOUND.value());
                        apiResponse.setEntity(savedNotification);
                    } else {
                        log.info("Member's token  does not exist");
                    }

                }
            }
            return apiResponse;
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    //Community
    public ApiResponse CreateServiceNotificationAllSelectedCommunity(NotificationDTO notificationDTO, Community comm) {
        try {
            ApiResponse apiResponse = new ApiResponse();
            if(!(comm == null)){
                List<Members> commMember = comm.getMembers();
                for (Members commMembersDetails : commMember) {
                    System.out.println("Member Number......."+ commMembersDetails.getMemberNumber());
                    Optional<Token> tokenOptional = tokenRepo.findByMemberNumber(commMembersDetails.getMemberNumber());
                    if (tokenOptional.isPresent()) {
                        Notification notification1 = new Notification();
                        notification1.setTitle(notificationDTO.getTitle());
                        notification1.setMessage(notificationDTO.getMessage());
                        notification1.setSubtitle(notificationDTO.getSubtitle());
                        Notification savedNotification = notificationRepo.save(notification1);
                        System.out.println("Checking token"+ tokenOptional.get().getDeviceToken());
                        saveTokensInNotification(savedNotification, tokenOptional.get());
                        apiResponse.setMessage(HttpStatus.FOUND.getReasonPhrase());
                        apiResponse.setStatusCode(HttpStatus.FOUND.value());
                        apiResponse.setEntity(savedNotification);
                    } else {
                        log.info("Member's token  does not exist");
                    }

                }
            }
            return apiResponse;
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }


    public ApiResponse CreateServiceNotification(Long groupId, Notification notification ) {
        try {
            ApiResponse apiResponse = new ApiResponse();
            Optional<Groups> groups  = groupsRepo.findByDeletedFlagAndId('N', groupId);
            if(groups.isEmpty()){
                return null;
            }
            List<GroupMember> groupMemberList = groupMemberRepo.getByGroup(groups.get());
            for(GroupMember groupMember : groupMemberList){
                Optional<Members> foundMembers = membersRepository.findByMemberNumber(groupMember.getMember().getMemberNumber());
                if (foundMembers.isPresent()) {
                    Optional<Token> tokenOptional = tokenRepo.findByMemberNumber(foundMembers.get().getMemberNumber());
                    if (tokenOptional.isPresent()) {
                        if (tokenOptional.get().isAllPermissionGranted()) {
                            Notification notification1 = new Notification();
                            notification1.setTitle(notification.getTitle());
                            notification1.setMessage(notification.getMessage());
                            notification1.setSubtitle(notification.getSubtitle());
                            notification1.setDateCreated(new Date());
                            notification1.setNextNotificationDate(new Date());
                            notification1.setNotificationCategory(NotificationCategory.SERVICE);
                            notification1.setNotificationType(notification.getNotificationType());
                            notification1.setNotificationFrequency(notification.getNotificationFrequency());
                            notification1.setNotificationStatus(notification.getNotificationStatus());
                            Notification savedNotification = notificationRepo.save(notification1);

                            saveTokensInNotification(savedNotification, tokenOptional.get());

                            apiResponse.setMessage(HttpStatus.FOUND.getReasonPhrase());
                            apiResponse.setStatusCode(HttpStatus.FOUND.value());
                            apiResponse.setEntity(savedNotification);
                        } else {
                            apiResponse.setMessage("Member has not enabled notifications");
                            apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
                        }
                    } else {
                        log.info("Member's token  does not exist");
                    }
                } else {
                    apiResponse.setMessage("User is unavailable");
                    apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
                }
            }
            return apiResponse;
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    public void saveTokensInNotification(Notification notification, Token token) {
        TokenNotifications tokenNotifications = new TokenNotifications();
        Optional<Notification> notificationOptional = notificationRepo.findById(notification.getId());
        if (notificationOptional.isPresent()) {
            Notification notification1 = notificationOptional.get();
            tokenNotifications.setNotification(notification1);
            Optional<Token> tokenOptional = tokenRepo.findById(token.getId());
            if (tokenOptional.isPresent()) {
                Token token1 = tokenOptional.get();
                List<TokenNotifications> tokenNotificationsList = notification.getTokenNotifications();

                tokenNotifications.setToken(token1);
                tokenNotifications.setTokenNotificationKey(new TokenNotificationKey());
                tokenNotificationsList.add(tokenNotifications);
                notification.setTokenNotifications(tokenNotificationsList);
                Notification notification2 = notificationRepo.save(notification);
                sendPushNotification(notification2, token1);
//                if (notification.getNotificationCategory() == NotificationCategory.SERVICE){
//                    scheduleNotification(notification2, token1);
//                } else {
//                    sendPushNotification(notification2, token1);
//                }
            }
        }
    }

//    private void scheduleNotification(Notification notification, Token token) {
//        if (notification.getNotificationFrequency() == NotificationFrequency.INSTANT){
//            sendPushNotification(notification, token);
//        } else if(notification.getNotificationFrequency() == NotificationFrequency.DAILY){
//            String newDate = updateServiceDateByDays(notification.getNextNotificationDate().toString());
//            System.out.println("Some date here " + newDate);
//            try {
////                Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                Date date = dateFormat.parse(newDate);
//                System.out.println("After simple date format" + date);
//                updateNextNotificationDate(date, notification, token);
//            } catch (ParseException e) {
//                log.info("Error" + e);
//                throw new IllegalStateException("Error parsing date");
//            }
//        } else if (notification.getNotificationFrequency() == NotificationFrequency.WEEKLY){
//            String newDate = updateServiceDateByWeeks(notification.getNextNotificationDate().toString());
//            try {
//                Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
//                updateNextNotificationDate(date1, notification, token);
//            } catch (ParseException e) {
//                log.info("Error" + e);
//                throw new IllegalStateException("Error parsing date");
//            }
//        } else if(notification.getNotificationFrequency() == NotificationFrequency.MONTHLY){
//            String newDate = updateServiceDateByMonths(notification.getNextNotificationDate().toString());
//            try {
//                Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
//                updateNextNotificationDate(date1, notification, token);
//            } catch (ParseException e) {
//                log.info("Error" + e);
//                throw new IllegalStateException("Error parsing date");
//            }
//        }
//    }

//    @Scheduled(cron = "5 * * * * *")
//    @Scheduled(cron = "0 00 06 * * *")
//
//    public void sendServiceNotification(){
//        try {
//            List<Notification> notificationList = notificationRepo.findNotificationsByDate(new Date());
//            if (notificationList.size() > 0){
//                for (int i = 0; i < notificationList.size(); i++){
//                    Notification notification = notificationList.get(i);
//                    if (notification.getNotificationCategory() == NotificationCategory.SERVICE){
//                        List<TokenNotifications> tokenNotifications = notification.getTokenNotifications();
//                        for (TokenNotifications tokenNotifications1 : tokenNotifications){
//                            Token token = tokenNotifications1.getToken();
//                            sendPushNotification(notification, token);
//                        }
//                    }
//                }
//            }
//            else {
//                log.info("No Notifications to send");
//            }
//        } catch (Exception e){
//            log.info("Error" + e);
//        }
//    }

    private void sendPushNotification(Notification notification, Token token) {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            Headers headers = new Headers.Builder()
                    .add("Content-Type", "application/json")
                    .add("Authorization", "Bearer " + "AAAA0mjUIIw:APA91bG28SaYX6ibPaRDd55Du9PUUcecHM2dpPVkkzPMhNMWYgBRVHnPQmvOK2gwlAjVxErioZ15aJEG04fU97sdkWBuJ1Wsyzw0a9VcuWWmJ6UcP1JMo0KcdcQTMk3FeEZ4ggEj-lpI")
                    .build();

            Map<String, Object> data = new HashMap<>();
            data.put("click_actions", "FLUTTER_NOTIFICATION_CLICK");
            data.put("status", "done");
            data.put("body", notification.getMessage());
            data.put("title", notification.getTitle());
            data.put("delivery_receipt_requested", true);

            Map<String, Object> notifications = new HashMap<>();
            notifications.put("title", notification.getTitle());
            notifications.put("body", notification.getMessage());
            notifications.put("android_channel_id", "EMT CHURCH");
            System.out.println("Notification Sent: " + notification.getTitle());


        Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("priority", "high");
            requestBody.put("data", data);
            requestBody.put("notification", notifications);
            requestBody.put("to", token.getDeviceToken());

            String jsonBody = new Gson().toJson(requestBody);

            RequestBody body = RequestBody.create(mediaType, jsonBody);
            Request request = new Request.Builder()
                    .url("https://fcm.googleapis.com/fcm/send")
                    .headers(headers)
                    .post(body)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    Optional<Notification> notification1 = notificationRepo.findById(notification.getId());
                    if (notification1.isPresent()) {
                        notification.setFirebaseFLag('Y');
                        notificationRepo.save(notification);
                    }
                    System.out.println("Notification was sent succesfully to .........................\n");
                } else {
                    log.info("An error occurred while trying to send a notification "+ response.body().string());
                }
            } catch (IOException e) {
                e.printStackTrace();
                // Handle exception
            }
        }

//    public ApiResponse<Notification> updateNotificationStatus(Long notificationId, NotificationStatus notificationStatus) {
//        try {
//            ApiResponse apiResponse = new ApiResponse<>();
//            Optional<Notification> notificationOptional = notificationRepo.findById(notificationId);
//            if (notificationOptional.isPresent()) {
//                Notification existingNotification = notificationOptional.get();
//                existingNotification.setNotificationStatus(notificationStatus);
//                Notification updatedStatus = notificationRepo.save(existingNotification);
//                apiResponse.setMessage(HttpStatus.FOUND.getReasonPhrase());
//                apiResponse.setStatusCode(HttpStatus.FOUND.value());
//                apiResponse.setEntity(updatedStatus);
//            } else {
//                apiResponse.setMessage(HttpStatus.NOT_MODIFIED.getReasonPhrase());
//                apiResponse.setStatusCode(HttpStatus.NOT_MODIFIED.value());
//            }
//            return apiResponse;
//        } catch (Exception e) {
//            log.info("Error" + e);
//            return null;
//        }
//    }

    public ApiResponse<Notification> getNotificationByMemberNumber(String memberNumber) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Optional<Token> token = tokenRepo.findByMemberNumber(memberNumber);
            if (token.isPresent()) {
                Long token1 = token.get().getId();
                List<Notification> notificationList = notificationRepo.findByTokenId(token1);
                if (notificationList.size() > 0) {
                    List<Notification> sentNotifications = notificationRepo.findSentNotifications();
                    if (sentNotifications.size() > 0) {

                        apiResponse.setMessage(HttpStatus.FOUND.getReasonPhrase());
                        apiResponse.setStatusCode(HttpStatus.FOUND.value());
                        apiResponse.setEntity(sentNotifications);
                    } else {
                        apiResponse.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                        apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
                    }
                } else {
                    apiResponse.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                    apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
                }
            } else {
                apiResponse.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            }
            return apiResponse;

        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }



//    private void updateNextNotificationDate(Date date1, Notification notification, Token token) {
//        try {
//            Optional<Notification> goalOptional = notificationRepo.findById(notification.getId());
//            if (goalOptional.isPresent()) {
//                Notification notification1 = goalOptional.get();
//               notification1.setNextNotificationDate(date1);
//                System.out.println("Updated Date " + notification1.getNextNotificationDate());
//                notificationRepo.save(notification1);
//                sendPushNotification(notification1, token);
//            }
//        } catch (Exception e) {
//            log.info("Error" + e);
//        }
//    }

//    public String updateDateByDays(String inputDateString) {
////        SimpleDateFormat inputDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar cal = Calendar.getInstance();
//        try {
//            cal.setTime(sdf.parse(inputDateString));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        cal.add(Calendar.DAY_OF_MONTH, 1);
//        return sdf.format(cal.getTime());
//    }
//    public String updateServiceDateByDays(String inputDateString) {
//        SimpleDateFormat inputDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
//        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//        Calendar cal = Calendar.getInstance();
//        try {
//            cal.setTime(inputDateFormat.parse(inputDateString));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        cal.add(Calendar.DAY_OF_MONTH, 1);
//        return outputDateFormat.format(cal.getTime());
//    }
//
//    public String updateServiceDateByWeeks(String inputDateString) {
//        SimpleDateFormat inputDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
//        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//        Calendar cal = Calendar.getInstance();
//        try {
//            cal.setTime(inputDateFormat.parse(inputDateString));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        cal.add(Calendar.WEEK_OF_MONTH, 1);
//        return outputDateFormat.format(cal.getTime());
//    }
//
//    public String updateServiceDateByMonths(String inputDateString) {
//        SimpleDateFormat inputDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
//        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//        Calendar cal = Calendar.getInstance();
//        try {
//            cal.setTime(inputDateFormat.parse(inputDateString));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        cal.add(Calendar.MONTH, 1);
//        return outputDateFormat.format(cal.getTime());
//    }
//
//    public String updateDateByWeeks(String currentDAte) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar cal = Calendar.getInstance();
//        try {
//            cal.setTime(sdf.parse(currentDAte));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        cal.add(Calendar.WEEK_OF_MONTH, 1);
//        return sdf.format(cal.getTime());
//    }
//
//    public String updateDateMonths(String currentDAte) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar cal = Calendar.getInstance();
//        try {
//            cal.setTime(sdf.parse(currentDAte));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        cal.add(Calendar.MONTH, 1);
//        return sdf.format(cal.getTime());
//    }
}
