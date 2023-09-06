package co.ke.emtechhouse.es.Advertisement;

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
@Table(name = "adverts")
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)

    private Long id;
    private String userName;
    private String phoneNumber;
    private String businessName;
    private  String fullName;
    private String businessDescription;
    private Long charges;
    private  String dateCreated;
    @Column(name = "isAvailable")
    private boolean isActive = true;


}
