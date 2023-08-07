package co.ke.emtechhouse.es.HelpComponent.ContactInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Data
@Table(name="contact_info")
public class Contact {
    @Id //unique id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private String email;
    private String phoneNumber;
    @Column(nullable = true)
    private String facebook;
    @Column(nullable = true)
    private String instagram;
    @Column(nullable = true)
    private String twitter;
    private String website;


}
