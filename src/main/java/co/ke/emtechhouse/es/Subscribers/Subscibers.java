package co.ke.emtechhouse.es.Subscribers;


import co.ke.emtechhouse.es.Subscriptions.Subscriptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Table(name = "subscribers")
public class Subscibers {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String phoneNumber;
    private String memberNumber;
    private Long subscriptionItemId;
    private  String dateSubscribed;
    @Column(name = "isActive")
    private boolean isActive = true;








}
