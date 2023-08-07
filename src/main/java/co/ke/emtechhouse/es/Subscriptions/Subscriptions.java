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
@Table(name = "subscriptions")
public class Subscriptions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String phoneNumber;
    private  String fullName;
    private String memberNumber;
    private Long charges;
    private  String dateCreated;
    @Column(name = "isActive")
    private boolean isActive = true;
}
