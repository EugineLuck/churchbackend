package co.ke.emtechhouse.es.MpesaIntergration.Services;

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
    private FailedRepo failedRepo;

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
        externalStkPushRequest.setPartyA(internalStkPushRequest.getTransactionNumber());
        externalStkPushRequest.setPartyB(mpesaConfiguration.getStkPushShortCode());
        externalStkPushRequest.setPhoneNumber(internalStkPushRequest.getTransactionNumber());
        externalStkPushRequest.setCallBackURL(mpesaConfiguration.getStkPushRequestCallbackUrl());
        externalStkPushRequest.setAccountReference(HelperUtility.getTransactionUniqueNumber());
        externalStkPushRequest.setTransactionDesc(String.format("%s Transaction", internalStkPushRequest.getTransactionNumber()));

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
            if (res.getResultCode() == "0") {

                System.out.println(res.getResultCode());
                Transaction transaction = new Transaction();
                transaction.setResultDesc(res.getResultDesc());
                transaction.setStatus("Processing");
                transaction.setResultCode(res.getResultCode());

                transaction.setTransactionAmount(Double.valueOf(internalStkPushRequest.getTransactionAmount()));
                transaction.setPhoneNumber(internalStkPushRequest.getTransactionNumber());

                transaction.setMemberNumber(internalStkPushRequest.getMemberNumber());
                transaction.setGivingId(internalStkPushRequest.getGivingId());
                transaction.setTransactionDate(new Date());
                transactionRepo.save(transaction);

                if (!transactionRepo.save(transaction).equals(null)) ;

                return res;
            } else {
                FailedTransactions failed = new FailedTransactions();
                failed.setResultDesc(res.getResultDesc());
                failed.setStatus("Processing");
                failed.setResultCode(res.getResultCode());

                failed.setTransactionAmount(Double.valueOf(internalStkPushRequest.getTransactionAmount()));
                failed.setPhoneNumber(internalStkPushRequest.getTransactionNumber());

                failed.setMemberNumber(internalStkPushRequest.getMemberNumber());
                failed.setGivingId(internalStkPushRequest.getGivingId());
                failed.setTransactionDate(new Date());
                failedRepo.save(failed);

                if (!failedRepo.save(failed).equals(null)) ;

                return res;
            }

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
}
