package co.ke.emtechhouse.es.MpesaIntergration.Mpesa_Express;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StkPushSyncResponse {
    @JsonProperty("MerchantRequestID")
    private String merchantRequestID;
    @JsonProperty("ResponseCode")
    private String responseCode;
    @JsonProperty("CustomerMessage")
    private String customerMessage;
    @JsonProperty("CheckoutRequestID")
    private String checkoutRequestID;
    @JsonProperty("ResponseDescription")
    private String responseDescription;
    @JsonProperty("ResultCode")
    private String resultCode;
}
