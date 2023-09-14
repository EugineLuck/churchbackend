package co.ke.emtechhouse.es.Groups;

import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Community.Community;
import co.ke.emtechhouse.es.Giving.Giving;
import co.ke.emtechhouse.es.Groups.GroupMemberComponent.GroupMember;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.cloud.storage.Acl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@ToString
public class Groups {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String groupName;
    private Long outStationId;
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

    @OneToMany(targetEntity = Giving.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "groupId", referencedColumnName = "id")
    private List<Giving> givings = new ArrayList<>();


}
