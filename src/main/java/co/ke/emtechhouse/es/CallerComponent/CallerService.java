package co.ke.emtechhouse.es.CallerComponent;//package co.ke.emtechhouse.es.CallerComponent;
//
//import co.ke.emtechhouse.es.DTO.MpesaExpress.MpesaexpressRequestDTO;
//import co.ke.emtechhouse.es.DTO.MpesaExpress.MpesaexpressResponseDTO;
//import co.ke.emtechhouse.es.DTO.MpesaWithdrawal.MpesaWithdrawRequestDTO;
//import co.ke.emtechhouse.es.DTO.MpesaWithdrawal.MpesaWithdrawResponseDTO;
//import co.ke.emtechhouse.es.DTO.MpesaWithdrawal.WithdrawalDTO;
//import com.google.gson.Gson;
//import lombok.extern.slf4j.Slf4j;
//import okhttp3.*;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.concurrent.TimeUnit;
//
//@Slf4j
//@Service
//public class CallerService {
//    @Value("${transaction.mpesa.mpesaexpress.requesturl}")
//    private String MPESA_EXPRESS_REQUEST_URL;
//    @Value("${transaction.mpesa.mpesaexpress.consumerkey}")
//    private String MPESA_EXPRESS_CONSUMER_KEY;
//    @Value("${transaction.mpesa.mpesaexpress.consumersecret}")
//    private String MPESA_EXPRESS_CONSUMER_SECRET;
//    @Value("${transaction.mpesa.mpesaexpress.password}")
//    private String MPESA_EXPRESS_PASSWORD;
//    @Value("${transaction.mpesa.mpesaexpress.environment}")
//    private String MPESA_EXPRESS_ENVIRONMENT;
//
//
//
//
//    @Value("${transaction.mpesa.mpesawithdraw.consumerkey}")
//    private String MPESA_WITHDRAW_CONSUMER_KEY;
//    @Value("${transaction.mpesa.mpesawithdraw.consumersecret}")
//    private String MPESA_WITHDRAW_CONSUMER_SECRET;
//    @Value("${transaction.mpesa.mpesawithdraw.initiatorname}")
//    private String MPESA_WITHDRAW_INITIATOR_NAME;
//    @Value("${transaction.mpesa.mpesawithdraw.securitycredential}")
//    private String MPESA_WITHDRAW_SECURITY_CREDENTIAL;
//    @Value("${transaction.mpesa.mpesawithdraw.commandid}")
//    private String MPESA_WITHDRAW_COMMAND_ID;
//    @Value("${transaction.mpesa.mpesawithdraw.queuetimeouturl}")
//    private String MPESA_WITHDRAW_QUEUE_TIME_OUT_URL;
//    @Value("${transaction.mpesa.mpesawithdraw.resulturl}")
//    private String MPESA_WITHDRAW_RESULT_URL;
//    @Value("${transaction.mpesa.mpesawithdraw.requesturl}")
//    private String MPESA_WITHDRAW_REQUEST_URL;
//    @Value("${transaction.mpesa.mpesawithdraw.part_a}")
//    private String MPESA_WITHDRAW_PART_A;
//    @Value("${transaction.mpesa.mpesawithdraw.occassion}")
//    private String MPESA_WITHDRAW_OCCASSION;
//
//
//
//    private final OkHttpClient client = new OkHttpClient();
//
//    public String callApi() throws IOException {
//        Request request = new Request.Builder()
//                .url("https://api.example.com/data")
//                .build();
//        Response response = client.newCall(request).execute();
//        String responseBody = response.body().string();
//        return responseBody;
//    }
//
//    public MpesaexpressResponseDTO initiateMpesaexpress(MpesaexpressRequestDTO mpesaexpressRequestDTO) {
//        try {
//            log.info("calling mpesa micro service for stk push");
//            String consumerkey = MPESA_EXPRESS_CONSUMER_KEY;
//            String password = MPESA_EXPRESS_PASSWORD;
//            String timestamp = "20230507122214";
//            String consumersecret = MPESA_EXPRESS_CONSUMER_SECRET;
//            String environment = MPESA_EXPRESS_ENVIRONMENT;
//            String url = MPESA_EXPRESS_REQUEST_URL;
//            log.info("url:=" + url);
//            OkHttpClient client = new OkHttpClient().newBuilder()
//                    .connectTimeout(100, TimeUnit.SECONDS)
//                    .readTimeout(300, TimeUnit.SECONDS)
//                    .build();
//            Gson g = new Gson();
//            String mpesaexpressRequestDTOSTR = g.toJson(mpesaexpressRequestDTO);
//            RequestBody body = RequestBody.create(
//                    MediaType.parse("application/json"), mpesaexpressRequestDTOSTR);
//            Request request = new Request.Builder()
//                    .url(url)
//                    .addHeader("consumerkey", consumerkey)
//                    .addHeader("password", password)
//                    .addHeader("timestamp", timestamp)
//                    .addHeader("consumersecret", consumersecret)
//                    .addHeader("environment", environment)
//                    .post(body)
//                    .build();
//            Response response = client.newCall(request).execute();
//            String res = response.body().string();
//            System.out.println("res" + res);
//            JSONObject joResp = new JSONObject(res);
//            System.out.println("joResp" +joResp);
//            JSONObject joBody = joResp.getJSONObject("response");
//            System.out.println("joBody" + joBody);
//            MpesaexpressResponseDTO mpesaexpressResponseDTO = new MpesaexpressResponseDTO();
//            mpesaexpressResponseDTO.setMerchantRequestID(joBody.getString("MerchantRequestID"));
//            mpesaexpressResponseDTO.setCheckoutRequestID(joBody.getString("CheckoutRequestID"));
//            mpesaexpressResponseDTO.setResponseCode(joBody.getString("ResponseCode"));
//            mpesaexpressResponseDTO.setResponseDescription(joBody.getString("ResponseDescription"));
//            mpesaexpressResponseDTO.setCustomerMessage(joBody.getString("CustomerMessage"));
//            return mpesaexpressResponseDTO;
//        } catch (Exception e) {
//            log.info("Caught Error {}" + e);
//            return null;
//        }
//    }
//
//    public MpesaWithdrawResponseDTO initiateMpesaWithdraw(WithdrawalDTO withdrawalDTO) {
//        try {
//            String consumerkey = MPESA_WITHDRAW_CONSUMER_KEY;
//            String consumersecret = MPESA_WITHDRAW_CONSUMER_SECRET;
//            String requesturl = MPESA_WITHDRAW_REQUEST_URL;
//            MpesaWithdrawRequestDTO mpesaWithdrawRequestDTO = new MpesaWithdrawRequestDTO();
//            mpesaWithdrawRequestDTO.setInitiatorName(MPESA_WITHDRAW_INITIATOR_NAME);
//            mpesaWithdrawRequestDTO.setSecurityCredential(MPESA_WITHDRAW_SECURITY_CREDENTIAL);
//            mpesaWithdrawRequestDTO.setCommandID(MPESA_WITHDRAW_COMMAND_ID);
//            mpesaWithdrawRequestDTO.setAmount(withdrawalDTO.getAmount());
//            mpesaWithdrawRequestDTO.setPaybill(MPESA_WITHDRAW_PART_A);
//            mpesaWithdrawRequestDTO.setPhoneNumber(withdrawalDTO.getPhoneNumber());
//            mpesaWithdrawRequestDTO.setRemarks("Cash Withdrawal");
//            mpesaWithdrawRequestDTO.setQueueTimeOutURL(MPESA_WITHDRAW_QUEUE_TIME_OUT_URL);
//            mpesaWithdrawRequestDTO.setResultURL(MPESA_WITHDRAW_RESULT_URL);
//            mpesaWithdrawRequestDTO.setOccassion(MPESA_WITHDRAW_OCCASSION);
//            OkHttpClient client = new OkHttpClient().newBuilder()
//                    .connectTimeout(100, TimeUnit.SECONDS)
//                    .readTimeout(300, TimeUnit.SECONDS)
//                    .build();
//            Gson g = new Gson();
//            String mpesaWithdrawRequestDTOSTR = g.toJson(mpesaWithdrawRequestDTO);
//            System.out.println("--------------------");
//            System.out.println(mpesaWithdrawRequestDTOSTR);
//            System.out.println(requesturl);
//            RequestBody body = RequestBody.create(
//                    MediaType.parse("application/json"), mpesaWithdrawRequestDTOSTR);
//            Request request = new Request.Builder()
//                    .url(requesturl)
//                    .addHeader("consumerkey", consumerkey)
//                    .addHeader("consumersecret", consumersecret)
//                    .post(body)
//                    .build();
//            Response response1 = client.newCall(request).execute();
//            System.out.println(response1);
//            String ress = response1.body().string();
////            System.out.println(ress);
//            JSONObject joResp = new JSONObject(ress);
//            JSONObject joBody = joResp.getJSONObject("response");
//            MpesaWithdrawResponseDTO mpesaWithdrawResponseDTO = new MpesaWithdrawResponseDTO();
//            mpesaWithdrawResponseDTO.setConversationID(joBody.getString("ConversationID"));
//            mpesaWithdrawResponseDTO.setOriginatorConversationID(joBody.getString("OriginatorConversationID"));
//            mpesaWithdrawResponseDTO.setResponseCode(joBody.getString("ResponseCode"));
//            mpesaWithdrawResponseDTO.setResponseDescription(joBody.getString("ResponseDescription"));
//            return mpesaWithdrawResponseDTO;
//        } catch (Exception e) {
//            log.info("Caught Error {}" + e);
//            return null;
//        }
//    }
//}
