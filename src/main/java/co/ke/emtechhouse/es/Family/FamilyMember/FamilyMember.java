package co.ke.emtechhouse.es.Family.FamilyMember;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@ToString
public class FamilyMember {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String familyMemberFName;
    private String familyMemberLName;
    private String dateOfBirth;
    private String memberGender;
    private Long familyId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Character deletedFlag = 'N';
    private String memberNumber;
//    @Enumerated(EnumType.STRING)
//    private FamilyMemberRole familyMemberRole ;
//    private String phoneNumber;
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date postedTime;

}
