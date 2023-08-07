package co.ke.emtechhouse.es.SmsComponent.Emtech.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class EmtSms {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String mobile;
    private String message;
    private String shortCode;
    private String serviceId;
    private String statusCode;
    private String statusCodeDesc;
    private String messageId;
    private String networkId;
    private String messageCost;
    private String creditBalance;
    @Column(nullable = false)
    private Date sentAt;
}
