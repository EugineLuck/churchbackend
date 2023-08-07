package co.ke.emtechhouse.es.MpesaIntergration.B2C_Transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InternalB2CTransactionRequest {

	@JsonProperty("PartyB")
	private String partyB;
	@JsonProperty("Amount")
	private String amount;
	@JsonProperty("Remarks")
	private String remarks;
	@JsonProperty("CommandID")
	private String commandID;
	@JsonProperty("Occassion")
	private String occassion;


}