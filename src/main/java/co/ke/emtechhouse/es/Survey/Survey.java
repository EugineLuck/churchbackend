package co.ke.emtechhouse.es.Survey;


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
@Table(name = "survey")
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String memberNumber;
    private String phoneNumber;

    private String attendanceFrequency;
    private  String worshipSatification;
    private String mostEnjoyed;
    private String contactMethod;
    private String themesForDiscusion;
    private  Long age;
    private String gender;
    private String churchAttendanceDuration;

    private  String dateCreated;
    @Column(name = "isAvailable")
    private boolean isActive = true;
}
