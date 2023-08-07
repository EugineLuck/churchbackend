package co.ke.emtechhouse.es.Deanery;

import co.ke.emtechhouse.es.Community.Community;
import co.ke.emtechhouse.es.Diocese.Diocese;
import co.ke.emtechhouse.es.Groups.Groups;
import co.ke.emtechhouse.es.Parish.Parish;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Table(name = "deanery")
public class Deanery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String deaneryName;
    private String location;


}
