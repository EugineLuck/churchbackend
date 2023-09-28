package co.ke.emtechhouse.es.Location;

import co.ke.emtechhouse.es.utils.Embeds;
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
@Table(name = "subcounty")
public class Subcounty {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String subcountyName;
    private Long subcountyID;
    private Long countyID;
    //
    @Embedded
    private Embeds others;



}
