package co.ke.emtechhouse.es.ServiceCaller;

import co.ke.emtechhouse.es.SmsComponent.Emtech.Dtos.Config.EmtSmsConfig;
import co.ke.emtechhouse.es.SmsComponent.Emtech.Dtos.Dtos.SendSmsReqDto;
import co.ke.emtechhouse.es.SmsComponent.Emtech.Dtos.Dtos.SendSmsRes;
import co.ke.emtechhouse.es.utils.Responses.EntityResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class ServiceCaller {
    private final EmtSmsConfig emtSmsConfig;
    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;

    public ServiceCaller(EmtSmsConfig emtSmsConfig, OkHttpClient okHttpClient, ObjectMapper objectMapper) {

        this.emtSmsConfig = emtSmsConfig;
        this.okHttpClient = okHttpClient;
        this.objectMapper = objectMapper;
    }


    // TODO: 5/17/2023  EMTECH SMS
    public EntityResponse sendSmsEmtech(SendSmsReqDto sendSmsReq){
        try{
            EntityResponse r = new EntityResponse();
            log.info("********************* Sending sms");
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(sendSmsReq);
            RequestBody body = RequestBody.create(json, JSON);



            EntityResponse entityResponse= new EntityResponse<>();

            String url= emtSmsConfig.getSendSmsUrl();

            log.info("url "+url);

            Request request = new Request.Builder()
                    .post(body)
                    .url(url)
                    .addHeader("userName", "String")
                    .addHeader("entityId", "001")
                    .build();
            Response response = client.newCall(request).execute();
            String res = response.body().string();

            Boolean isJsonValid= isJSONValid(res);
            if(isJsonValid){
                System.out.println("Email response ::" + res);

//                JSONObject etyResponse = new JSONObject(res);
                JSONArray etyResponse = new JSONArray(res);

//                SendSmsRes sendSmsResponse= objectMapper.readValue(etyResponse.toString(),
//                        SendSmsRes.class);

                SendSmsRes[] sendSmsResponse = objectMapper.readValue(etyResponse.toString(),
                        SendSmsRes[].class);



                r.setMessage(HttpStatus.OK.getReasonPhrase());
                r.setStatusCode(HttpStatus.OK.value());
                r.setEntity(sendSmsResponse[0]);
            }else {
                r.setMessage(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase());
                r.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                r.setEntity(null);
            }
            return r;

        }catch (Exception e){
            log.info("*********************error");
            log.info("Caught Error {}"+e.getMessage());

            EntityResponse response = new EntityResponse();
            response.setMessage(e.getLocalizedMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setEntity(e.getCause());
            return response;
        }
    }

    public boolean isJSONValid(String json) {
        try {
            new JSONObject(json);
        } catch (JSONException e) {
            try {
                new JSONArray(json);
            } catch (JSONException ne) {
                return false;
            }
        }
        return true;
    }
}
