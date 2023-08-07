package co.ke.emtechhouse.es.SmsComponent.Emtech.Dtos;

import co.ke.emtechhouse.es.ServiceCaller.ServiceCaller;
import co.ke.emtechhouse.es.SmsComponent.Emtech.Dtos.Config.EmtSmsConfig;
import co.ke.emtechhouse.es.SmsComponent.Emtech.Dtos.Dtos.SendSmsReqDto;
import co.ke.emtechhouse.es.SmsComponent.Emtech.Dtos.Dtos.SendSmsRes;
import co.ke.emtechhouse.es.SmsComponent.Emtech.Dtos.Dtos.SmsDto;
import co.ke.emtechhouse.es.utils.Responses.EntityResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class EmtSmsService {
    @Autowired
    private EmtSmsRepo emtSmsRepo;


    public EmtSmsService(OkHttpClient okHttpClient, ObjectMapper objectMapper, EmtSmsConfig smsConfig, ServiceCaller serviceCaller) {
        this.okHttpClient = okHttpClient;
        this.objectMapper = objectMapper;
        this.smsConfig = smsConfig;
        this.serviceCaller = serviceCaller;
    }

    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;
    private final EmtSmsConfig smsConfig;
    private final ServiceCaller serviceCaller;


    public EntityResponse sendSms(SmsDto smsDto){
        try {
            EntityResponse res= new EntityResponse<>();


            SendSmsReqDto req= new SendSmsReqDto();
            req.setApi_key(smsConfig.getApi_key());
            req.setService_id(smsConfig.getService_id());
            log.info("phone number is :: "+ smsDto.getMsisdn());
            req.setMobile(smsDto.getMsisdn());
            req.setResponse_type(smsConfig.getResponse_type());
            req.setShortcode(smsConfig.getShortcode());
            req.setMessage(smsDto.getText());



            res=serviceCaller.sendSmsEmtech(req);
            if(res.getStatusCode().equals(HttpStatus.OK.value())){{
                SendSmsRes smsRes= (SendSmsRes) res.getEntity();

                EmtSms emtSms= new EmtSms(null,smsRes.getMobile_number(),smsDto.getText(), smsConfig.getShortcode(),
                        smsConfig.getService_id(),smsRes.getStatus_code(),smsRes.getStatus_desc(),smsRes.getMessage_id(), smsRes.getNetwork_id(),
                        smsRes.getMessage_cost(),smsRes.getCredit_balance(),new Date());
                EmtSms savedEmtSms=emtSmsRepo.save(emtSms);

                res.setStatusCode(HttpStatus.CREATED.value());
                res.setMessage("Message sent status "+smsRes.getStatus_desc());
                res.setEntity(savedEmtSms);
            }}
            return res;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }
}
