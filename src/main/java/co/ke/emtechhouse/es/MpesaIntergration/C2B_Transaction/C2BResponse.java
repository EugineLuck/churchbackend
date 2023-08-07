package co.ke.emtechhouse.es.MpesaIntergration.C2B_Transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class C2BResponse {
    @JsonProperty("ConversationID")
    private String conversationID;
    @JsonProperty("ResponseDescription")
    private String responseDescription;
    @JsonProperty("ResponseCode")
    private String responseCode;
    @JsonProperty("OriginatorCoversationID")
    private String originatorCoversationID;
}
