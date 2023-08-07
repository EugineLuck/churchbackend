package co.ke.emtechhouse.es.USSD;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "user")
public class USSD {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String firstName;
    private String lastName;
    private String idOwnership;
    private String gender;
    private String memberRole;
    private String nationalID;
    private Long outStationId;
    private String phoneNumber;
    private Long communityId;
    private Long familyId;
    private Long groupsId;
    private String memberNumber;

}
