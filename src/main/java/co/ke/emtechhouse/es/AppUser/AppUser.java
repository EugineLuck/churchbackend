package co.ke.emtechhouse.es.AppUser;

import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Auth.Roles.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@ToString
@Table(name = "AppUser")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Transient
    private String accessToken;
    private String userName;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;


    @Column(name = "active")
    private boolean isActive = true;
    @Column(name = "first_login", nullable = false, length = 1)
    private Character firstLogin = 'y';
    @Column(name = "locked")
    private boolean isAcctLocked;
    @JsonIgnore
    private Character deletedFlag = 'N';

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "modifiedBy", nullable = true, length=256)
    private String modifiedBy;

    @Column(name = "modifiedOn", nullable = true)
    private String modifiedOn;


    @Lob
    private String imageBanner;

}
