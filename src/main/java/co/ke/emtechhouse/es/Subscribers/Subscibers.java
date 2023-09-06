package co.ke.emtechhouse.es.Subscribers;


import co.ke.emtechhouse.es.Subscriptions.Subscriptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

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

    // a list of all subscritions subscribed to;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "subscriber_subscription", joinColumns = @JoinColumn(name = "subscriber_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "subscription_id", referencedColumnName = "id"))
    private List<Subscriptions> subscriptions;



}
