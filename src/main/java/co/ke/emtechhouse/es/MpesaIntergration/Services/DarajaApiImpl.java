package co.ke.emtechhouse.es.MpesaIntergration.Services;

import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Auth.Members.MembersController;
import co.ke.emtechhouse.es.Auth.Members.MembersRepository;
import co.ke.emtechhouse.es.Giving.Giving;
import co.ke.emtechhouse.es.Giving.GivingRepo;
import co.ke.emtechhouse.es.MpesaIntergration.B2C_Transaction.B2CTransactionRequest;
import co.ke.emtechhouse.es.MpesaIntergration.B2C_Transaction.B2CTransactionSyncResponse;
import co.ke.emtechhouse.es.MpesaIntergration.B2C_Transaction.InternalB2CTransactionRequest;
import co.ke.emtechhouse.es.MpesaIntergration.C2B_Transaction.C2BRequest;
import co.ke.emtechhouse.es.MpesaIntergration.C2B_Transaction.C2BResponse;
import co.ke.emtechhouse.es.MpesaIntergration.Config.MpesaConfiguration;
import co.ke.emtechhouse.es.MpesaIntergration.Mpesa_Express.*;
import co.ke.emtechhouse.es.MpesaIntergration.OAUTH_Token.AccessTokenResponse;
import co.ke.emtechhouse.es.MpesaIntergration.Register_URL.RegisterUrlRequest;
import co.ke.emtechhouse.es.MpesaIntergration.Register_URL.RegisterUrlResponse;
import co.ke.emtechhouse.es.MpesaIntergration.Transaction;
import co.ke.emtechhouse.es.MpesaIntergration.TransactionRepo;
import co.ke.emtechhouse.es.MpesaIntergration.FailedRepo;
import co.ke.emtechhouse.es.MpesaIntergration.FailedTransactions;
import co.ke.emtechhouse.es.MpesaIntergration.Utils.HelperUtility;
import co.ke.emtechhouse.es.SmsComponent.Emtech.Dtos.Dtos.SmsDto;
import co.ke.emtechhouse.es.SmsComponent.Emtech.Dtos.EmtSmsService;
import co.ke.emtechhouse.es.Subscriptions.SubsPaymentsRepository;
import co.ke.emtechhouse.es.Subscriptions.SubscriptionPayments;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import static co.ke.emtechhouse.es.MpesaIntergration.Utils.Constants.*;


@Service
@Slf4j
public class DarajaApiImpl implements DarajaApi {
    private final MpesaConfiguration mpesaConfiguration;
    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    @Autowired
    private TransactionRepo transactionRepo;

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
    private FailedRepo failedRepo;
    @Autowired
    SubsPaymentsRepository subsPaymentsRepository;

    @Autowired
    public DarajaApiImpl(MpesaConfiguration mpesaConfiguration, OkHttpClient okHttpClient, ObjectMapper objectMapper) {
        this.mpesaConfiguration = mpesaConfiguration;
        this.okHttpClient = okHttpClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public AccessTokenResponse getAccessToken() {
        String encodedCredentials = HelperUtility.toBase64String(String.format("%s:%s", mpesaConfiguration.getConsumerKey(),
                mpesaConfiguration.getConsumerSecret()));

        Request request = new Request.Builder()
                .url(String.format("%s?grant_type=%s", mpesaConfiguration.getOauthEndpoint(), mpesaConfiguration.getGrantType()))
                .get()
                .addHeader(AUTHORIZATION_HEADER_STRING, String.format("%s %s", BASIC_AUTH_STRING, encodedCredentials))
                .addHeader(CACHE_CONTROL_HEADER, CACHE_CONTROL_HEADER_VALUE)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            assert response.body() != null;
            return objectMapper.readValue(response.body().string(), AccessTokenResponse.class);
        } catch (IOException e) {
            log.error(String.format("Could not get access token. -> %s", e.getLocalizedMessage()));
            return null;
        }
    }


//    public PullTransactionRegisterResponse getPullRequest(){
//        AccessTokenResponse accessTokenResponse = getAccessToken();
//
//        PullTransactionRegisterRequest pullTransactionRegisterRequest = new PullTransactionRegisterRequest();
//        pullTransactionRegisterRequest.setRequestType(mpesaConfiguration.getRequestType());
//        pullTransactionRegisterRequest.setShortCode(mpesaConfiguration.getShortCode());
//        pullTransactionRegisterRequest.setCallBackURL();
//        pullTransactionRegisterRequest.setNominatedNumber();
//
//        return null;
//    }

    @Override
    public RegisterUrlResponse registerUrl() {
        AccessTokenResponse accessTokenResponse = getAccessToken();

        RegisterUrlRequest registerUrlRequest = new RegisterUrlRequest();
        registerUrlRequest.setConfirmationURL(mpesaConfiguration.getConfirmationURL());
        registerUrlRequest.setResponseType(mpesaConfiguration.getResponseType());
        registerUrlRequest.setShortCode(mpesaConfiguration.getShortCode());
        registerUrlRequest.setValidationURL(mpesaConfiguration.getValidationURL());

        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE,
                Objects.requireNonNull(HelperUtility.toJson(registerUrlRequest)));

        Request request = new Request.Builder()
                .url(mpesaConfiguration.getRegisterUrl())
                .post(body)
                .addHeader("Authorization", String.format("%s %s", BEARER_AUTH_STRING, accessTokenResponse.getAccessToken()))
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();

            assert response.body() != null;
            return objectMapper.readValue(response.body().string(), RegisterUrlResponse.class);
        } catch (IOException e) {
            log.error(String.format("Could not register url ->%s", e.getLocalizedMessage()));
            return null;
        }
    }

    @Override
    public C2BResponse C2BTransaction(C2BRequest c2BRequest) {
        AccessTokenResponse accessTokenResponse = getAccessToken();
        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE,
                Objects.requireNonNull(HelperUtility.toJson(c2BRequest)));
        System.out.println(accessTokenResponse.toString());

        Request request = new Request.Builder()
                .url(mpesaConfiguration.getC2BTransaction())
                .post(body)
                .addHeader(AUTHORIZATION_HEADER_STRING, String.format("%s %s", BEARER_AUTH_STRING, accessTokenResponse.getAccessToken()))
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            assert response.body() != null;
            // use Jackson to Decode the ResponseBody ...

            return objectMapper.readValue(response.body().string(), C2BResponse.class);
        } catch (IOException e) {
            log.error(String.format("Could not complete transaction -> %s", e.getLocalizedMessage()));
            return null;
        }
    }

    @Override
    public B2CTransactionSyncResponse b2CTransaction(InternalB2CTransactionRequest internalB2CTransactionRequest) {
        AccessTokenResponse accessTokenResponse = getAccessToken();

        B2CTransactionRequest b2CTransactionRequest = new B2CTransactionRequest();

        b2CTransactionRequest.setCommandID(internalB2CTransactionRequest.getCommandID());
        b2CTransactionRequest.setAmount(internalB2CTransactionRequest.getAmount());
        b2CTransactionRequest.setPartyB(internalB2CTransactionRequest.getPartyB());
        b2CTransactionRequest.setRemarks(internalB2CTransactionRequest.getRemarks());
        b2CTransactionRequest.setOccassion(internalB2CTransactionRequest.getOccassion());

        //security credentials
        b2CTransactionRequest.setSecurityCredential(HelperUtility.getSecurityCredentials(mpesaConfiguration.getB2cInitiatorPassword()));

        //result URL
        b2CTransactionRequest.setResultURL(mpesaConfiguration.getB2cResultUrl());
        b2CTransactionRequest.setQueueTimeOutURL(mpesaConfiguration.getB2cQueueTimeoutUrl());
        b2CTransactionRequest.setInitiatorName(mpesaConfiguration.getB2cInitiatorName());
        b2CTransactionRequest.setPartyA(mpesaConfiguration.getShortCode());

        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE,
                Objects.requireNonNull(HelperUtility.toJson(b2CTransactionRequest)));

        Request request = new Request.Builder()
                .url(mpesaConfiguration.getB2cTransactionEndpoint())
                .post(body)
                .addHeader(AUTHORIZATION_HEADER_STRING, String.format("%s %s", BEARER_AUTH_STRING, accessTokenResponse.getAccessToken()))
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            assert response.body() != null;
            var b = objectMapper.readValue(response.body().string(), B2CTransactionSyncResponse.class);
            return b;
//            return objectMapper.readValue(response.body().string(), B2CTransactionSyncResponse.class);
        } catch (IOException e) {
            log.error(String.format("Could not complete transaction ->%s", e.getLocalizedMessage()));
            return null;
        }
    }


    @Override
    public StkPushSyncResponse stkPushTransaction(InternalStkPushRequest internalStkPushRequest) {

        ExternalStkPushRequest externalStkPushRequest = new ExternalStkPushRequest();
        externalStkPushRequest.setBusinessShortCode(mpesaConfiguration.getStkPushShortCode());

        String transactionTimestamp = HelperUtility.getTransactionTimestamp();
        String stkPushPassword = HelperUtility.getStkPushPassword(mpesaConfiguration.getStkPushShortCode(),
                mpesaConfiguration.getStkPassKey(), transactionTimestamp);

        externalStkPushRequest.setPassword(stkPushPassword);
        externalStkPushRequest.setTimestamp(transactionTimestamp);
        externalStkPushRequest.setTransactionType(CUSTOMER_PAYBILL_ONLINE);
        externalStkPushRequest.setAmount(internalStkPushRequest.getTransactionAmount());
        externalStkPushRequest.setPartyA(getfomatedPhoneNumber(internalStkPushRequest.getTransactionNumber()));
        externalStkPushRequest.setPartyB(mpesaConfiguration.getStkPushShortCode());
        externalStkPushRequest.setPhoneNumber(getfomatedPhoneNumber(internalStkPushRequest.getTransactionNumber()));
        externalStkPushRequest.setCallBackURL(mpesaConfiguration.getStkPushRequestCallbackUrl());
        externalStkPushRequest.setAccountReference(HelperUtility.getTransactionUniqueNumber());
        externalStkPushRequest.setTransactionDesc(String.format("%s Transaction", getfomatedPhoneNumber(internalStkPushRequest.getTransactionNumber())));

        AccessTokenResponse accessTokenResponse = getAccessToken();

        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE,
                Objects.requireNonNull(HelperUtility.toJson(externalStkPushRequest)));

        Request request = new Request.Builder()
                .url(mpesaConfiguration.getStkPushRequestUrl())
                .post(body)
                .addHeader(AUTHORIZATION_HEADER_STRING, String.format("%s %s", BEARER_AUTH_STRING, accessTokenResponse.getAccessToken()))
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            assert response.body() != null;
            var v = objectMapper.readValue(response.body().string(), StkPushSyncResponse.class);

            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            InternalStkPushStatusRequest chid = new InternalStkPushStatusRequest();
            chid.setCheckoutRequestID(v.getCheckoutRequestID());
            StkPushStatusResponse res = this.stkPushStatus(chid);

                System.out.println(res.getResultCode());
                    if (internalStkPushRequest.getTransactionType() == "subscription") {

                        //                Subscriptions
                        SubscriptionPayments subscriptionPayments = new SubscriptionPayments();
                        subscriptionPayments.setMemberNumber(internalStkPushRequest.getMemberNumber());
                        subscriptionPayments.setAmountPaid(internalStkPushRequest.getTransactionAmount());
                        subscriptionPayments.setDatePaid(String.valueOf(new Date()));
                        subsPaymentsRepository.save(subscriptionPayments);

                    } else {
                        Optional<Members> members1 = membersRepository.findByMemberNumber(internalStkPushRequest.getMemberNumber());
                        Optional<Giving> give = givingRepo.findById(internalStkPushRequest.getGivingId());
                        if (members1.isPresent()) {
                            Members members = members1.get();
                            Giving giving = give.get();
                        Transaction transaction = new Transaction();
                        transaction.setResultDesc(res.getResultDesc());
                        transaction.setStatus("Processing");
                        transaction.setResultCode(res.getResultCode());

                        transaction.setTransactionAmount(internalStkPushRequest.getTransactionAmount());
                        transaction.setTransactionNumber(getfomatedPhoneNumber(internalStkPushRequest.getTransactionNumber()));

                        transaction.setMemberNumber(internalStkPushRequest.getMemberNumber());

                        transaction.setGivingId(internalStkPushRequest.getGivingId());
                        transaction.setTransactionDate(new Date());
                        transaction.setTransactionMode("M-pesa");
                        transactionRepo.save(transaction);
                        String message = "Dear "+ members.getFirstName() + members.getLastName() +  " Giving for  " + giving.getGivingLevel() + " " + giving.getGivingTitle() +  " ! " + "at Muumini Church was sucessfully recorded ";
                        emtSmsService.sendSms(new SmsDto(members.getPhoneNumber(), message));

                    }  }
//


            return res;
        } catch (IOException e) {
            log.error(String.format("Could not perform the STK push request -> %s", e.getLocalizedMessage()));
            return null;
        }
    }



    @Override
    public StkPushStatusResponse stkPushStatus(InternalStkPushStatusRequest internalStkPushStatusRequest) {

//        StkPushStatusResponse stkPushStatusResponse = new StkPushStatusResponse();
        StkPushStatusRequest stkPushStatusRequest = new StkPushStatusRequest();
        String transactionTimestamp = HelperUtility.getTransactionTimestamp();
        String stkPushPassword = HelperUtility.getStkPushPassword(mpesaConfiguration.getStkPushShortCode(),
                mpesaConfiguration.getStkPassKey(), transactionTimestamp);

        stkPushStatusRequest.setCheckoutRequestID(internalStkPushStatusRequest.getCheckoutRequestID());
        stkPushStatusRequest.setPassword(stkPushPassword);
        stkPushStatusRequest.setBusinessShortCode(mpesaConfiguration.getStkPushShortCode());
        stkPushStatusRequest.setTimestamp(transactionTimestamp);

        AccessTokenResponse accessTokenResponse = getAccessToken();

        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE,
                Objects.requireNonNull(HelperUtility.toJson(stkPushStatusRequest)));

        Request request = new Request.Builder()
                .url(mpesaConfiguration.getStkPushTransactionStatus())
                .post(body)
                .addHeader(AUTHORIZATION_HEADER_STRING, String.format("%s %s", BEARER_AUTH_STRING, accessTokenResponse.getAccessToken()))
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            assert response.body() != null;
            return objectMapper.readValue(response.body().string(), StkPushStatusResponse.class);

        } catch (IOException e) {
            log.error(String.format("Could not confirm transaction status->%s", e.getLocalizedMessage()));
            return null;
        }
    }


    public  String getfomatedPhoneNumber(String input) {
        // Ensure the input string is not null and has at least 9 characters
        if (input != null && input.length() >= 9) {
            // Use substring to get the last 9 characters
            return "254"+input.substring(input.length() - 9);
        } else {
            // Handle invalid input gracefully
            return "Invalid input";
        }
    }
}
