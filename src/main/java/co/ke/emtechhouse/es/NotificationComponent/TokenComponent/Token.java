package co.ke.emtechhouse.es.NotificationComponent.TokenComponent;

import co.ke.emtechhouse.es.NotificationComponent.TokenNotifications.TokenConsents;
import co.ke.emtechhouse.es.NotificationComponent.TokenNotifications.TokenNotifications;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Data
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(nullable = true)
    private String deviceToken;
    @Column
    private String location = null;
    private boolean allPermissionGranted=true;
    private boolean familyPermissionGranted=true;
    private boolean groupPermissionGranted=true;
    private boolean personalPermissionGranted=true;
    private boolean servicePermissionGranted=true;
    private String memberNumber;

    @OneToMany(mappedBy = "token", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    List<TokenNotifications> tokenNotifications;
}
