package co.ke.emtechhouse.es.DTO.MpesaExpress.MpesaExpressDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MpesaexpresscallbackDTO {
    @JsonProperty("Body")
    public MpesaexpressbodyresultsDTO getBody() {
        return Body;
    }

    public void setBody(MpesaexpressbodyresultsDTO body) {
        Body = body;
    }

    private MpesaexpressbodyresultsDTO Body;
}
