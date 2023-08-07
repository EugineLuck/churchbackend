package co.ke.emtechhouse.es.MpesaIntergration.B2C_Transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class B2CTransactionSyncResponse {

    @JsonProperty("ConversationID")
    private String conversationID;

    @JsonProperty("ResponseCode")
    private String responseCode;

    @JsonProperty("ResponseDescription")
    private String responseDescription;

    @JsonProperty("OriginatorConversationID")
    private String originatorConversationID;
}
