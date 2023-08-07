package co.ke.emtechhouse.es.MpesaIntergration.Mpesa_Express;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StkPushStatusResponse extends StkPushSyncResponse {

	@JsonProperty("MerchantRequestID")
	private String merchantRequestID;

	@JsonProperty("ResponseCode")
	private String responseCode;

	@JsonProperty("CheckoutRequestID")
	private String checkoutRequestID;

	@JsonProperty("ResponseDescription")
	private String responseDescription;

	@JsonProperty("ResultDesc")
	private String resultDesc;

	@JsonProperty("ResultCode")
	private String resultCode;
}