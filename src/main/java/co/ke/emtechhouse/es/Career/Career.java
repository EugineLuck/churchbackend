package co.ke.emtechhouse.es.Career;

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
@Table(name = "careers")
public class Career {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String userName;
    private String phoneNumber;
    private String userOccupation;
    private String careerDescription;
    private  String dateCreated;
    @Column(name = "isAvailable")
    private boolean isActive = true;




}
