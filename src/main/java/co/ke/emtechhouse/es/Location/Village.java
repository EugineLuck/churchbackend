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
@Table(name = "village")
public class Village {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String villageName;
    private Long wardID;

    @Embedded
    private Embeds others;
}
