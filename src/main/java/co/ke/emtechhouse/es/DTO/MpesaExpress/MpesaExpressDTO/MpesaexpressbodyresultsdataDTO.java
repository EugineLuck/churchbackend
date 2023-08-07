package co.ke.emtechhouse.es.DTO.MpesaExpress.MpesaExpressDTO;


import com.fasterxml.jackson.annotation.JsonProperty;

public class MpesaexpressbodyresultsdataDTO {

    private String MerchantRequestID;//": "29115-34620561-1",
    private String CheckoutRequestID;//": "ws_CO_191220191020363925",
    private String ResultCode;//": 0,
    private String ResultDesc;//": "The service request is processed successfully.",
    private MpesaexpressbodyresultsdataitemsDTO CallbackMetadata;//": {
    @JsonProperty("MerchantRequestID")

    public String getMerchantRequestID() {
        return MerchantRequestID;
    }

    public void setMerchantRequestID(String merchantRequestID) {
        MerchantRequestID = merchantRequestID;
    }
    @JsonProperty("CheckoutRequestID")
    public String getCheckoutRequestID() {
        return CheckoutRequestID;
    }

    public void setCheckoutRequestID(String checkoutRequestID) {
        CheckoutRequestID = checkoutRequestID;
    }
    @JsonProperty("ResultCode")
    public String getResultCode() {
        return ResultCode;
    }

    public void setResultCode(String resultCode) {
        ResultCode = resultCode;
    }
    @JsonProperty("ResultDesc")
    public String getResultDesc() {
        return ResultDesc;
    }

    public void setResultDesc(String resultDesc) {
        ResultDesc = resultDesc;
    }
    @JsonProperty("CallbackMetadata")
    public MpesaexpressbodyresultsdataitemsDTO getCallbackMetadata() {
        return CallbackMetadata;
    }

    public void setCallbackMetadata(MpesaexpressbodyresultsdataitemsDTO callbackMetadata) {
        CallbackMetadata = callbackMetadata;
    }
}
