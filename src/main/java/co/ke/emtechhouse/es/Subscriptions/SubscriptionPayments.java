package co.ke.emtechhouse.es.Subscriptions;


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
@Table(name = "SubscriptionPayments")
public class SubscriptionPayments {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String memberNumber;
    private Double amountPaid;
    private  String datePaid;
    @Column(name = "isActive")
    private boolean isActive = true;


}
