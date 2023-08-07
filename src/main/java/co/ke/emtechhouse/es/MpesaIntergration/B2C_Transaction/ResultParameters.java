package co.ke.emtechhouse.es.MpesaIntergration.B2C_Transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResultParameters{
	@JsonProperty("ResultParameter")
	private List<ResultParameterItem> resultParameter;
}