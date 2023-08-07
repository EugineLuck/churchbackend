package co.ke.emtechhouse.es.SmsComponent.Emtech.Dtos.Dtos;

import lombok.Data;

@Data
public class SendSmsReqDto {
    private String api_key;
    private String service_id;
    private String mobile;
    private String response_type;
    private String shortcode;
    private String message;
}
