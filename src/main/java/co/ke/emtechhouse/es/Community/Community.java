package co.ke.emtechhouse.es.Community;

import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Deanery.Deanery;
import co.ke.emtechhouse.es.Diocese.Diocese;
import co.ke.emtechhouse.es.Family.Family;
import co.ke.emtechhouse.es.OutStation.OutStation;
import co.ke.emtechhouse.es.Parish.Parish;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@ToString
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String communityName;
    private String communityLocation;
    private Long outStationId;

//    @OneToMany(targetEntity = Members.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "communityId", referencedColumnName = "id")
//    private List<Members> members = new ArrayList<>();

//    @OneToOne(mappedBy = "community" ,  fetch = FetchType.LAZY)
//    private Members members;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Character postedFlag;
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date postedTime;
//    @Column(nullable = false, length = 200)
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    private String postedBy;
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Character verifiedFlag;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(nullable = false)
    private Character deletedFlag;
    private String modifiedBy;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(nullable = false)
    private Character modifiedFlag='N';
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date modifiedTime;

    @OneToMany(targetEntity = Family.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "communityId", referencedColumnName = "id")
    private List<Family> families = new ArrayList<>();

    @OneToMany(targetEntity = Members.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "communityId", referencedColumnName = "id")
    private List<Members> members = new ArrayList<>();
}
