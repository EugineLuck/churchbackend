package co.ke.emtechhouse.es.MpesaIntergration;

import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Auth.Members.MembersController;
import co.ke.emtechhouse.es.Auth.Members.MembersRepository;
import co.ke.emtechhouse.es.Auth.Members.MembersService;
import co.ke.emtechhouse.es.Auth.Security.jwt.CurrentUserContext;
import co.ke.emtechhouse.es.DTO.MpesaExpress.MpesaExpressDTO.MpesaexpresscallbackDTO;
import co.ke.emtechhouse.es.DTO.MpesaExpress.MpesaexpressRequestDTO;
import co.ke.emtechhouse.es.DTO.MpesaExpress.MpesaexpressResponseDTO;
import co.ke.emtechhouse.es.Giving.Giving;
import co.ke.emtechhouse.es.Giving.GivingRepo;


import co.ke.emtechhouse.es.MpesaIntergration.Mpesa_Express.StkPushStatusResponse;
import co.ke.emtechhouse.es.MpesaIntergration.Mpesa_Express.StkPushSyncResponse;
import co.ke.emtechhouse.es.NotificationComponent.*;
import co.ke.emtechhouse.es.NotificationComponent.TokenComponent.Token;
import co.ke.emtechhouse.es.NotificationComponent.TokenComponent.TokenRepo;


import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.SmsComponent.Emtech.Dtos.Dtos.SmsDto;
import co.ke.emtechhouse.es.SmsComponent.Emtech.Dtos.EmtSmsService;
import co.ke.emtechhouse.es.Stages.Stages;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class TransactionService {
//    @Value("${transaction.jenga.account_number}")
//    private String EASY_SAVE_ACCOUNT;


    @Autowired
    TransactionRepo transactionRepo;
    @Autowired
    MembersController membersController;
    @Autowired
    MembersRepository membersRepository;

    @Autowired
    private EmtSmsService emtSmsService;

//    @Autowired
//    StkPushStatusResponse stkPushStatusResponse;

    @Autowired
    GivingRepo givingRepo;


    @Autowired
    TokenRepo tokenRepo;
    @Autowired
    NotificationRepo notificationRepo;
    @Autowired
    NotificationService notificationService;


    @Autowired
    MpesaCallerService mpesaCallerService;

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    String nowDate = now.format(formatter);







    public String initializeTransactionCode() {
        String timestamp = new SimpleDateFormat("yymmssSSS").format(new Date()); // 9 character timestamp

        char[] characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        Random random = new SecureRandom();
        char[] result = new char[5];

        for (int i = 0; i < result.length; i++) {
            int randomCharIndex = random.nextInt(characterSet.length);
            result[i] = characterSet[randomCharIndex];
        }
        return new String(result).concat(timestamp); // 14 character length
    }

    public static String generateRandomDigits() {
        Random random = new Random();
        int length = 12;
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10); // Generate a random digit between 0 and 9
            sb.append(digit);
        }

        return sb.toString();
    }



    public ApiResponse enter2(Transaction transaction) {

        try {
            ApiResponse response = new ApiResponse();
            Optional<Members> members1 = membersRepository.findByMemberNumber(transaction.getMemberNumber());
            Optional<Giving> give = givingRepo.findById(transaction.getGivingId());
            if (members1.isPresent()) {
                Members members = members1.get();
                Giving giving = give.get();

                Transaction cash = new Transaction();

                cash.setResultDesc("Cash Giving");
                cash.setStatus("Recorded");
                cash.setResultCode("0");

                cash.setTransactionAmount(transaction.getTransactionAmount());
                cash.setMemberNumber(transaction.getMemberNumber());
                if (transaction.getChequeNumber() != null) {
                    cash.setChequeNumber(transaction.getChequeNumber());
                }
                if (transaction.getEnvelopeNumber() != null) {
                    cash.setEnvelopeNumber(transaction.getEnvelopeNumber());
                }
                if (transaction.getTransactionNumber() != null) {
                    cash.setTransactionNumber(transaction.getTransactionNumber());
                }

                cash.setGivingId(transaction.getGivingId());
                cash.setTransactionDate(new Date());
                cash.setTransactionMode(transaction.getTransactionMode());



                Transaction saveTransaction =  transactionRepo.save(cash);


                response.setMessage("Transaction Entered Successfully. Transaction for member " + transaction.getMemberNumber());
                response.setStatusCode(HttpStatus.CREATED.value());

                NotificationDTO notif = new NotificationDTO();
                notif.setMessage("Giving transaction was updated Successfully");
                notif.setTitle("Giving Update");
                notif.setNotificationtype("All");
                notif.setSubtitle("Giving status");
                System.out.println("cheking if noti---------------"+notif);
                notificationService.CreateServiceNotificationforMember(notif, transaction.getMemberNumber());

                response.setEntity(saveTransaction);

                String message = "Dear " + members.getFirstName() + members.getLastName() + " Giving for  " + giving.getGivingLevel() + " " + giving.getGivingTitle() + " ! " + "at Muumini Church was sucessfully recorded ";
                emtSmsService.sendSms(new SmsDto(members.getPhoneNumber(), message));
            } else {

                response.setMessage("MemberNumber not found");
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                return response;

            }


            return response;
        } catch (Exception e) {
            ApiResponse res = new ApiResponse();
            log.error(e.getMessage());
            res.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
            res.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return res;
        }
    }



    public ApiResponse callback(StkPushStatusResponse StkPushStatusResponse) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            System.out.println("check0000"+ StkPushStatusResponse);
            JSONObject joRes = new JSONObject(StkPushStatusResponse);
            System.out.println("shhhhshhshhs" + joRes);
            JSONObject joRespBody = joRes.getJSONObject("body"); // new JSONObject(mpesaexpresscallbackDTO);
            System.out.println("kskskksks1"+ joRespBody);
            JSONObject joResp = joRespBody.getJSONObject("stkCallback");
            String resultCode = joResp.getString("resultCode");
            System.out.println("resultcose" + resultCode);

            String resultDesc = joResp.getString("checkoutRequestID");
            System.out.println("ResultDesc" + resultDesc);
            if (resultCode.equalsIgnoreCase("0")) {
                JSONObject CallbackMetadata = joResp.getJSONObject("callbackMetadata");
                JSONArray Item = CallbackMetadata.getJSONArray("item");
                JSONObject mpesaReceiptData = Item.getJSONObject(1);
                String mpesaReceipt = mpesaReceiptData.getString("value");
                JSONObject mpesaDateData = Item.getJSONObject(3);
                String mpesaDate = mpesaDateData.getString("value");


            }

            return apiResponse;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return apiResponse;
        }
    }



    private void notifySuccessMember(Transaction transaction) {
        try {
            Optional<Transaction> trans = transactionRepo.findById(transaction.getId());
            if (trans.isPresent()) {
                Optional<Token> token = tokenRepo.findByMemberNumber(transaction.getMemberNumber());
                if (token.isPresent()) {
                    Notification notification = new Notification();
                    notification.setMessage("Transaction for \"" + trans.get().getGivingId() + "\", amount:" + transaction.getTransactionAmount() + " is successful");
                    notification.setTitle("Successful Transaction");
                    notification.setNotificationFrequency(NotificationFrequency.INSTANT);
                    notification.setDateCreated(new Date());
                    notification.setNotificationCategory(NotificationCategory.TRANSACTION);
                    Notification notification1 = notificationRepo.save(notification);
                    notificationService.saveTokensInNotification(notification1, token.get());
                } else {
                    log.info("Token Not Found");
                }
            } else {
                log.info("Goal Not Found");
            }

        } catch (Exception e) {
            log.info("Error: " + e);
        }
    }

    private void notifyFailureMember(Transaction transaction) {
        try {
            Optional<Transaction> trans = transactionRepo.findById(transaction.getId());
            if (trans.isPresent()) {
                Optional<Token> token = tokenRepo.findByMemberNumber(transaction.getMemberNumber());
                if (token.isPresent()) {
                    Notification notification = new Notification();
                    notification.setMessage("Transaction for \"" + trans.get().getGivingId() + "\", amount:" + transaction.getTransactionAmount() + ", has failed.Please try again Later");
                    notification.setTitle("Failed Transaction");

                    notification.setNotificationFrequency(NotificationFrequency.INSTANT);
                    notification.setDateCreated(new Date());
                    notification.setNotificationCategory(NotificationCategory.TRANSACTION);
                    Notification notification1 = notificationRepo.save(notification);
                    notificationService.saveTokensInNotification(notification1, token.get());

                } else {
                    log.info("Token Not Found");
                }
            } else {
                log.info("Goal Not Found");
            }

        } catch (Exception e) {
            log.info("Error: " + e);
        }
    }





    public ApiResponse<?> getAllTransactions() {
        ApiResponse response = new ApiResponse();
        try {

                List<SuccessfullyTransactions> transactions = transactionRepo.findAllTransations();
                if (transactions.size() > 0) {
                    response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                    response.setStatusCode(HttpStatus.FOUND.value());
                    response.setEntity(transactions);
                } else {
                    response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                    response.setStatusCode(HttpStatus.NOT_FOUND.value());
                }
//            }
            return response;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }


    public ApiResponse<?> getMemberTransactions() {
        ApiResponse response = new ApiResponse();
        try {

                List<SuccessfullyTransactions> successfullyTransactions = transactionRepo.getAllSucessfullyTransactions();
                if (successfullyTransactions.size() > 0) {
                    response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                    response.setStatusCode(HttpStatus.FOUND.value());
                    response.setEntity(successfullyTransactions);
                } else {
                    response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                    response.setStatusCode(HttpStatus.NOT_FOUND.value());
                }
//            }
            return response;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    public ApiResponse<Transaction> getTransactionsByGivingId(Long givingId) {
        ApiResponse response = new ApiResponse();
        try {

                Optional<Transaction> transaction = transactionRepo.findById(givingId);
                if (transaction.isPresent()) {
                    response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                    response.setStatusCode(HttpStatus.FOUND.value());
                    response.setEntity(transaction);
                } else {
                    response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                    response.setStatusCode(HttpStatus.NOT_FOUND.value());
                }
//            }
            return response;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }
//    public ApiResponse<Transaction> getTransactionsByPhoneNumber(String phoneNumber) {
//        ApiResponse response = new ApiResponse();
//        try {
//
//                List<Transaction> transaction = transactionRepo.findByPhoneNumber(phoneNumber);
//                if (transaction.isPresent()) {
//                    response.setMessage(HttpStatus.FOUND.getReasonPhrase());
//                    response.setStatusCode(HttpStatus.FOUND.value());
//                    response.setEntity(transaction);
//                } else {
//                    response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
//                    response.setStatusCode(HttpStatus.NOT_FOUND.value());
//                }
////            }
//            return response;
//        } catch (Exception e) {
//            log.info("Catched Error {} " + e);
//            return null;
//        }
//    }
//
//
//    public ApiResponse getTransactionsByMemberNumber(String memberNumber) {
//        ApiResponse apiResponse = new ApiResponse<>();
//        try {
//            Optional<Transaction> transactions = transactionRepo.findByMemberNumber(memberNumber);
//            apiResponse.setEntity(transactions);
//            apiResponse.setMessage("List of transactions ");
//            apiResponse.setStatusCode(200);
//        } catch (Exception e) {
//            apiResponse.setMessage("Error fetching transactions");
//            apiResponse.setStatusCode(404);
//        }
//        return apiResponse;
//    }
public ApiResponse<Stages> getByMemberNumber(String memberNumber) {
    try {
        ApiResponse response = new ApiResponse();
        Optional<Members> member = membersRepository.findByMemberNumber(memberNumber);
        if (member.isPresent()) {
            Members presentMembers = member.get();
            System.out.println(member);

            List<SuccessfullyTransactions> transactions = transactionRepo.findByMemberNumber(memberNumber);
            if (transactions.size() > 0) {
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(transactions);
            } else {
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }
        } else {
            response.setMessage("Transaction not found");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
        }

        return response;
    } catch (Exception e) {
        log.info("Catched Error {} " + e);
        return null;
    }
}public ApiResponse<Stages> getByGivingId(Long givingId) {
    try {
        ApiResponse response = new ApiResponse();
        Optional<Giving> giving = givingRepo.findById(givingId);
        if (giving.isPresent()) {
            Giving presentGiving = giving.get();
            System.out.println(presentGiving);

            List<SuccessfullyTransactions> transactions = transactionRepo.findByGivingId(givingId);
            if (transactions.size() > 0) {
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(transactions);
            } else {
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }
        } else {
            response.setMessage("Transaction not found");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
        }

        return response;
    } catch (Exception e) {
        log.info("Catched Error {} " + e);
        return null;
    }
}

}
