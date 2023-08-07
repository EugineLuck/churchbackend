package co.ke.emtechhouse.es.MpesaIntergration.Register_URL;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegisterUrlRequest {
    @JsonProperty("ShortCode")
    private String shortCode;

    @JsonProperty("ResponseType")
    private String responseType;

    @JsonProperty("ConfirmationURL")
    private String confirmationURL;

    @JsonProperty("ValidationURL")
    private String validationURL;

}
