package co.ke.emtechhouse.es.MpesaIntergration.B2C_Transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReferenceItem{
	@JsonProperty("Value")
	private String value;
	@JsonProperty("Key")
	private String key;
}
