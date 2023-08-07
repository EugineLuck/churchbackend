package co.ke.emtechhouse.es.MpesaIntergration.Mpesa_Express;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItemItem{

	@JsonProperty("Value")
	private Object value;

	@JsonProperty("Name")
	private String name;
}