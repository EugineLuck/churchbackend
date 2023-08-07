package co.ke.emtechhouse.es.SmsComponent.Emtech.Dtos.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "emtech.sms")
public class EmtSmsConfig {
    private String sendSmsUrl;
    private String api_key;
    private String service_id;
    private String response_type;
    private String shortcode;
}
