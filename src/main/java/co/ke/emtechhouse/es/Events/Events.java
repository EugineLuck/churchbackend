package co.ke.emtechhouse.es.Events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.checkerframework.checker.optional.qual.OptionalBottom;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@ToString
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String eventName;
    private String participants;
    private Date datePosted;
    private String eventDate;
    private String requirements;
    private String description;
    private boolean status;
}
