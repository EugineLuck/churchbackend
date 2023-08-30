package co.ke.emtechhouse.es.Auth.Members;

import co.ke.emtechhouse.es.AppUser.AppUser;
import co.ke.emtechhouse.es.Auth.Roles.Role;
import co.ke.emtechhouse.es.NotificationComponent.TokenComponent.Token;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

import javax.persistence.*;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Table(name = "members")
public class Members {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false)
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Long familyId;
    private Long communityId;
    private Long outStationId;
    private String modeOfRegistration;
    private String memberRole;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "token_id")
    private Token token;

//    @Transient
//    private List<GroupNameDTO> groupNames;


    @GeneratedValue
    @Column(unique = true)
    private String memberNumber;


    private String dateOfBirth;
    @Column(name = "active")
    private boolean isActive = true;
    @Column(name = "first_login", nullable = false, length = 1)
    private Character firstLogin = 'y';
    @Column(name = "locked")
    private boolean isAcctLocked;
    private Boolean isAdmin = false;
    private Long roleFk;

    @Column(name = "postedTime", nullable = false)
    private String postedTime;
    private String nationalID;
    private Long appId;
    private String idOwnership;
    private String gender;



    @Column(name = "loggedIn")
    private String isLoggedIn = "N";
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "members_roles",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();



    @JsonIgnore
    private String deletedBy;
    @JsonIgnore
    private String deletedFlag;
    @JsonIgnore
    private Date deletedTime;

    @Column(name = "modifiedBy", nullable = true, length=256)
    private String modifiedBy;

    @Column(name = "modifiedOn", nullable = true)
    private String modifiedOn;

//    @Column(name = "deleteFlag", nullable = false, length = 100)
//    private String deleteFlag;




//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "appUser_id", referencedColumnName = "id")
//    private AppUser appUser;

}

