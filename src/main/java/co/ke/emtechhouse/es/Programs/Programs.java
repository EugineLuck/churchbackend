package co.ke.emtechhouse.es.Programs;

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
@Table(name = "programs")
public class Programs {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)

    private Long id;
    private String outStationID;
    private String day;
    private  String startTime;
    private  String endTime;
    private  String programName;

    @Column(name = "isActive")
    private boolean isActive = true;

}
