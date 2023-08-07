package co.ke.emtechhouse.es.Parish;

import co.ke.emtechhouse.es.Community.Community;
import co.ke.emtechhouse.es.Deanery.Deanery;
import co.ke.emtechhouse.es.Groups.Groups;
import co.ke.emtechhouse.es.OutStation.OutStation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@ToString
public class Parish {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String parishName;
    private String parishLocation;

//    @OneToMany(targetEntity = OutStation.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "parishId", referencedColumnName = "id")
//    private List<OutStation> outStations = new ArrayList<>();


}