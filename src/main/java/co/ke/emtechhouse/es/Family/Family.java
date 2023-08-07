package co.ke.emtechhouse.es.Family;

import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Family.FamilyMember.FamilyMember;
import co.ke.emtechhouse.es.Giving.Giving;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@ToString
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String familyName;
    private Long outStationId;
    private Long communityId;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date postedTime;
    @GeneratedValue
    @Column(unique = true)
    private String familyNumber;
    private String memberNumber;

    @OneToMany(targetEntity = FamilyMember.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "familyId", referencedColumnName = "id")
    private List<FamilyMember> familyMembers = new ArrayList<>();

    public void setFamilyMember(List<FamilyMember> familyMembersList) {
    }

    public Collection<Object> getFamilyMember() {
        return null;
    }

    @OneToMany(targetEntity = Members.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "familyId", referencedColumnName = "id")
    private List<Members> members = new ArrayList<>();


//    @OneToMany(targetEntity = Giving.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "familyId", referencedColumnName = "id")
//    private List<Giving> givings = new ArrayList<>();
}
