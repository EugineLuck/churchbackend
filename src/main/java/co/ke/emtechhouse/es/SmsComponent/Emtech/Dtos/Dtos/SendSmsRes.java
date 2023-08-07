package co.ke.emtechhouse.es.SmsComponent.Emtech.Dtos.Dtos;

import lombok.Data;

@Data
public class SendSmsRes {
    private String status_code;
    private String status_desc;
    private String message_id;
    private String mobile_number;
    private String network_id;
    private String message_cost;
    private String credit_balance;
}
