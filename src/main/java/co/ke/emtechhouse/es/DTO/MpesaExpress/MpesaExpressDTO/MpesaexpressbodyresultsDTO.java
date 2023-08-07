package co.ke.emtechhouse.es.DTO.MpesaExpress.MpesaExpressDTO;


import com.fasterxml.jackson.annotation.JsonProperty;

public class MpesaexpressbodyresultsDTO {
      @JsonProperty("stkCallback")
      public MpesaexpressbodyresultsdataDTO getStkCallback() {
            return stkCallback;
      }

      public void setStkCallback(MpesaexpressbodyresultsdataDTO stkCallback) {
            this.stkCallback = stkCallback;
      }

      private MpesaexpressbodyresultsdataDTO stkCallback;
}
