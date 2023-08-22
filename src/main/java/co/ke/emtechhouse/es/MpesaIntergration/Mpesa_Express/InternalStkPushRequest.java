package co.ke.emtechhouse.es.MpesaIntergration.Mpesa_Express;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InternalStkPushRequest {
    @JsonProperty("transactionAmount")
    private Double transactionAmount;
    @JsonProperty("memberNumber")
    private String memberNumber;
    @JsonProperty("transactionNumber")
    private String transactionNumber;
    @JsonProperty("transactionCode")
    private String transactionCode;
    @JsonProperty("transactionDate")
    private String transactionDate;
    @JsonProperty("givingId")
    private String givingId;
}
