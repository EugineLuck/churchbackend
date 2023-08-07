//package co.ke.emtechhouse.es.CallerComponent;
//
//import co.ke.emtechhouse.es.DTO.MpesaExpress.MpesaexpressRequestDTO;
//import co.ke.emtechhouse.es.DTO.MpesaExpress.MpesaexpressResponseDTO;
//import com.google.gson.Gson;
//import lombok.extern.slf4j.Slf4j;
//import okhttp3.*;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.TimeUnit;
//
//@Slf4j
//@Service
//public class MpesaCallerService {
//    @Value("${transaction.mpesa.mpesaexpress.requesturl}")
//    private String MPESA_EXPRESS_REQUEST_URL;
//
//    @Value("${transaction.mpesa.mpesawithdraw.requesturl}")
//    private String MPESA_WITHDRAW_REQUEST_URL;
//
//    public MpesaexpressResponseDTO initiateMpesaexpress(MpesaexpressRequestDTO mpesaexpressRequestDTO) {
//        try {
//            log.info("calling mpesa micro service for stk push");
//            String url = MPESA_EXPRESS_REQUEST_URL;
//            log.info("url:=" + url);
//            OkHttpClient client = new OkHttpClient().newBuilder()
//                    .connectTimeout(100, TimeUnit.SECONDS)
//                    .readTimeout(300, TimeUnit.SECONDS)
//                    .build();
//            Gson g = new Gson();
//            String mpesaexpressRequestDTOSTR = g.toJson(mpesaexpressRequestDTO);
//            System.out.println("99999999999999999999999999999999");
//            System.out.println(mpesaexpressRequestDTOSTR);
//            RequestBody body = RequestBody.create(
//                    MediaType.parse("application/json"), mpesaexpressRequestDTOSTR);
//            Request request = new Request.Builder()
//                    .url(url)
//                    .post(body)
//                    .build();
//            Response response = client.newCall(request).execute();
//            String res = response.body().string();
//            System.out.println("res" + res);
//            JSONObject joResp = new JSONObject(res);
//            System.out.println("joResp" +joResp);
//
//
//
//
//            MpesaexpressResponseDTO mpesaexpressResponseDTO = new MpesaexpressResponseDTO();
//            mpesaexpressResponseDTO.setMerchantRequestID(joResp.getString("MerchantRequestID"));
//            mpesaexpressResponseDTO.setCheckoutRequestID(joResp.getString("CheckoutRequestID"));
//            mpesaexpressResponseDTO.setResponseCode(joResp.getString("ResponseCode"));
//            mpesaexpressResponseDTO.setResponseDescription(joResp.getString("ResponseDescription"));
//            mpesaexpressResponseDTO.setCustomerMessage(joResp.getString("CustomerMessage"));
//            return mpesaexpressResponseDTO;
//        } catch (Exception e) {
//            log.info("Caught Error {}" + e);
//            return null;
//        }
//    }
//
//
//}
