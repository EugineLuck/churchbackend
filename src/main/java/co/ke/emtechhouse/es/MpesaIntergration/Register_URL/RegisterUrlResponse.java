package co.ke.emtechhouse.es.MpesaIntergration.Register_URL;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RegisterUrlResponse {
    @JsonProperty("ConversationID")
    private String conversationID;

    @JsonProperty("ResponseDescription")
    private String responseDescription;

    @JsonProperty("OriginatorCoversationID")
    private String originatorCoversationID;

//    @SneakyThrows
//    @Override
//    public String toString(){
//
//        return new ObjectMapper().writeValueAsString(this);
//    }
}
