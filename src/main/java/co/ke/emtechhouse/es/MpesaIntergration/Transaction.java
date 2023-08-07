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


    private Date transactionDate;
    private String phoneNumber;
    private Double transactionAmount;
    private Long givingId;
    private String memberNumber;

    private String status;
    private String merchantRequestID;
    private String checkoutRequestID;
    private String responseCode;
    private String responseDescription;
    private String customerMessage;
//    after callback
    private String mpesaReceiptNumber;
    private String paymentTransactionDate;
    // withdrawal


    //fin
//    private String tranStatus;
    private String requestId;
    private String transId;}

