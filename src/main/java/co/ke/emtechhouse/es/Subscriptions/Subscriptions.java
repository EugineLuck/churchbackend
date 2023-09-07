package co.ke.emtechhouse.es.Subscriptions;


import co.ke.emtechhouse.es.Subscribers.Subscibers;
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
@Table(name = "subscriptions")
public class Subscriptions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String phoneNumber;
    private  String fullName;
    private String memberNumber;
    private String subscriptionType;
    private Double charges;
    private String descriptionInfo;
    @Lob
    private String banner;


    private  String dateCreated;
    @Column(name = "isActive")
    private boolean isActive = true;

    //A list of all subscribers subcribed
    @ManyToMany(mappedBy = "subscriptionsSet")
    Set<Subscibers> subscibersSet;


    
}
