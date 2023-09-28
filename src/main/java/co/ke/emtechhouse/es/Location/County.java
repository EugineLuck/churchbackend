
//package co.ke.emtechhouse.es.Location;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//
//import java.util.List;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@ToString
//public class County {
//    private String countyName;
//    private String countyCapital;
//    private String countyCode;
//    private List subCounties;
//}

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
@Table(name = "county")
public class County {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String countyName;
    private Long countyID;
//
    @Embedded
    private Embeds others;



}

