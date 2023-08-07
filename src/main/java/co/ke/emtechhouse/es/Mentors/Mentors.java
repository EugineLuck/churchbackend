package co.ke.emtechhouse.es.Mentors;

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
@Table(name = "mentors")
public class Mentors {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String userName;
    private String phoneNumber;
    private String mentorLocation;
    private String mentorEmail;
    @Lob
    private String mentorBanner;
    private  String dateCreated;
    @Column(name = "isAvailable")
    private boolean isActive = true;
}
