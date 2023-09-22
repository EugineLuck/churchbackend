package co.ke.emtechhouse.es.MpesaIntergration;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String transactionCode;

//    private Date transactionDate;
    private String transactionNumber;
    private Double transactionAmount;
    private Long givingId;
    private String memberNumber;
    private String chequeNumber;
    private String envelopeNumber;
    private String transactionMode;
    private String resultCode;
    private String resultDesc;
    private String status;
    private String datePaid;

}



