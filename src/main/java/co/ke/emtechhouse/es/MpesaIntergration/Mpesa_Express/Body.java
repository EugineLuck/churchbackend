package co.ke.emtechhouse.es.MpesaIntergration.Mpesa_Express;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Body{

	@JsonProperty("stkCallback")
	private StkCallback stkCallback;
}