package co.ke.emtechhouse.es.DTO.MpesaWithdrawal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MpesaWithdrawRequestDTO {
    private String InitiatorName; //": "testapi",
    private String SecurityCredential; //": "E451p3VhlcxxH659L5IL5TDw6vc5Mh2WA3/edHj5KNzlewftA4jx2JtdjymSPQMdpkrWQN4QDYrB0LpVUlVbopFRinPI/L10zRuUOu4DzHKr0mMboCu84PZmWGXD8BX/tCWwbYi+Z+Xf22jQZWlvfHb4AqjiC/9fSbAqlf+tWn+LE+R01xOP0KKJGd2mnDxiWvkHzkdd8j76sg5TrcZerUAr+NmPsLoiCrAHq17f6GZG86DmY9rWlQ7imEf/nLHKDvlHgvmbCGsR1i5p/VWJB8ZFCOK2uABuX7QzHYTenZGXr61LFLQT2Jnypwm9q9dye07fTwNaCXLojRscyTu8hQ==",
    private String CommandID; //": "BusinessPayment",
    private Double Amount;//": 1,
//    private String PartyA; //": 600584,
//    private String PartyB; //": 254708374149,
    private String PhoneNumber;// take paybill
    private String Paybill;// take phone
    private String Remarks ; //": "Test remarks",
    private String QueueTimeOutURL; //": "https://mydomain.com/b2c/queue",
    private String ResultURL; //": "https://mydomain.com/b2c/result",
    private String Occassion; //": "null"

}
