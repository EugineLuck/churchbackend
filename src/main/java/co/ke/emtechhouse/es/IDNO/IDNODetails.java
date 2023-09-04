package co.ke.emtechhouse.es.IDNO;


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
@Table(name = "verifiedDetails")
public class IDNODetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String documentType;
    private String documentNumber;
    private String countryCode;
    private String fullName;
    private String birthDate;
    private String documentSerialNumber;
    private String locationName;
    private String villageName;


}
