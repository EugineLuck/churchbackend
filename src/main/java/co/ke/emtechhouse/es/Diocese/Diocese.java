package co.ke.emtechhouse.es.Diocese;

import co.ke.emtechhouse.es.Community.Community;
import co.ke.emtechhouse.es.Deanery.Deanery;
import co.ke.emtechhouse.es.Groups.Groups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@ToString
@Table(name = "diocese")
public class Diocese {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String dioceseName;
    private String dioceseLocation;

}