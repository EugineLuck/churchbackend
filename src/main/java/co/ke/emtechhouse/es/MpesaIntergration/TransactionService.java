package co.ke.emtechhouse.es.MpesaIntergration;

import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Auth.Members.MembersRepository;
import co.ke.emtechhouse.es.Auth.Security.jwt.CurrentUserContext;
import co.ke.emtechhouse.es.DTO.MpesaExpress.MpesaExpressDTO.MpesaexpresscallbackDTO;
import co.ke.emtechhouse.es.DTO.MpesaExpress.MpesaexpressRequestDTO;
import co.ke.emtechhouse.es.DTO.MpesaExpress.MpesaexpressResponseDTO;
import co.ke.emtechhouse.es.Giving.Giving;
import co.ke.emtechhouse.es.Giving.GivingRepo;


import co.ke.emtechhouse.es.MpesaIntergration.Mpesa_Express.StkPushStatusResponse;
import co.ke.emtechhouse.es.NotificationComponent.*;
import co.ke.emtechhouse.es.NotificationComponent.TokenComponent.Token;
import co.ke.emtechhouse.es.NotificationComponent.TokenComponent.TokenRepo;


import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.Stages.Stages;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;

import java.util.*;

@Service
@Slf4j
public class TransactionService {
//    @Value("${transaction.jenga.account_number}")
//    private String EASY_SAVE_ACCOUNT;


    @Autowired
    TransactionRepo transactionRepo;
    @Autowired
    MembersRepository membersRepository;


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




//                          TODO: Call mpesa initiate transaction request
                            MpesaexpressRequestDTO mpesaexpressRequestDTO = new MpesaexpressRequestDTO();
                            System.out.println("sffs");
//                                            mpesaexpressRequestDTO.setBusinessShortCode();
//                                            mpesaexpressRequestDTO.setTransactionType(MPESA_EXPRESS_BUSINESS_TRANSACTION_TYPE);
                            mpesaexpressRequestDTO.setAmount(String.valueOf(Math.round(transaction.getTransactionAmount())));
                            mpesaexpressRequestDTO.setPhoneNumber(transaction.getPhoneNumber());
                            System.out.println(transaction.getPhoneNumber());
//                                            mpesaexpressRequestDTO.setAccountReference(transaction.getWalletNumber()); //wallet
                            System.out.println(mpesaexpressRequestDTO);
//                                            mpesaexpressRequestDTO.setCallBackURL(MPESA_EXPRESS_CALLBACK_URL);
//                                            mpesaexpressRequestDTO.setTransactionDesc("Saving Schedule Payment");
                            MpesaexpressResponseDTO mpesaresponse = mpesaCallerService.initiateMpesaexpress(mpesaexpressRequestDTO);
                            System.out.println("cheeeeeeetsssssss");
                            System.out.println(mpesaresponse);
//                  TODO: Update the transaction entry with transaction details
//                            transaction.setMerchantRequestID(mpesaresponse.getMerchantRequestID());
//                            transaction.setCheckoutRequestID(mpesaresponse.getCheckoutRequestID());
//                            transaction.setResponseCode(mpesaresponse.getResponseCode());
//                            transaction.setResponseDescription(mpesaresponse.getResponseDescription());
//                            transaction.setCustomerMessage(mpesaresponse.getCustomerMessage());
                            transaction.setStatus("Processing");
                            //upishi


                            transaction.setPhoneNumber(String.valueOf(Long.valueOf(transaction.getPhoneNumber())));

                            System.out.println("checkkkkkkkk");
                            response.setMessage("Transaction Entered Successfully. Transaction code is " + transaction.getTransactionCode());
                            response.setStatusCode(HttpStatus.CREATED.value());
                            Transaction saveTransaction = transactionRepo.save(transaction);
                            response.setEntity(saveTransaction);

                            if (Objects.equals(transaction.getStatus(), "Success")){



                                notifySuccessMember(transaction);
                            } else {
                                notifyFailureMember(transaction);
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

                List<Transaction> transactions = transactionRepo.findAll();
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
