package co.ke.emtechhouse.es.MpesaIntergration.B2C_Transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReferenceData{
	@JsonProperty("ReferenceItem")
	private ReferenceItem referenceItem;
}
