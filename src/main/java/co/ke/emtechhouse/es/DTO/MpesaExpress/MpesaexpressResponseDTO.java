package co.ke.emtechhouse.es.DTO.MpesaExpress;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MpesaexpressResponseDTO {
    private String MerchantRequestID;//": "29115-34620561-1",
    private String CheckoutRequestID;//": "ws_CO_191220191020363925",
    private String ResponseCode;//": "0",
    private String ResponseDescription;//": "Success. Request accepted for processing",
    private String CustomerMessage;//": "Success. Request accepted for processing"
}
