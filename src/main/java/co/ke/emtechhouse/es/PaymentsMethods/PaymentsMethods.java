package co.ke.emtechhouse.es.PaymentsMethods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Table(name = "paymentsMethods")
public class PaymentsMethods {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String methodName;
    private  String dateCreated;
    @Column(name = "isActive")
    private boolean isActive = true;

}
