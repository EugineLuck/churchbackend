package co.ke.emtechhouse.es.PrayerRequests;

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
@Table(name = "prayerRequests")
public class PrayerRequests {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)

    private Long id;
    private String prayerRequest;
    private String requestedBy;
    private  String dateRequested;
    @Column(name = "isActive")
    private boolean isActive = true;


}
