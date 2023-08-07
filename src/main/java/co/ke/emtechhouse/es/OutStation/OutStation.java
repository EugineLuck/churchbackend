package co.ke.emtechhouse.es.OutStation;

import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Community.Community;
import co.ke.emtechhouse.es.Family.Family;
import co.ke.emtechhouse.es.Groups.Groups;
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

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@ToString
public class OutStation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String outStationName;
    private String outStationLocation;
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

    @OneToMany(targetEntity = Community.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "outStationId", referencedColumnName = "id")
    private List<Community> communities = new ArrayList<>();

    @OneToMany(targetEntity = Family.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "outStationId", referencedColumnName = "id")
    private List<Family> families = new ArrayList<>();

    @OneToMany(targetEntity = Members.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "outStationId", referencedColumnName = "id")
    private List<Members> members = new ArrayList<>();

    @OneToMany(targetEntity = Groups.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "outStationId", referencedColumnName = "id")
    private List<Groups> groups = new ArrayList<>();
}
