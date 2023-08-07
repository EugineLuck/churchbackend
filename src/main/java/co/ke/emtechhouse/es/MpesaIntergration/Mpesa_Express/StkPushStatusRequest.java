package co.ke.emtechhouse.es.MpesaIntergration.Mpesa_Express;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StkPushStatusRequest{

	@JsonProperty("CheckoutRequestID")
	private String checkoutRequestID;

	@JsonProperty("BusinessShortCode")
	private String businessShortCode;

	@JsonProperty("Timestamp")
	private String timestamp;

	@JsonProperty("Password")
	private String password;
}