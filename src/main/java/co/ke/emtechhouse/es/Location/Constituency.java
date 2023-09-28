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
@Table(name = "constituency")
public class Constituency {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String constituencyName;
    private Long constituencyID;
    private Long subcountyID;
    //
    @Embedded
    private Embeds others;



}
