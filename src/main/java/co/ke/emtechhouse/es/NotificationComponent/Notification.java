package co.ke.emtechhouse.es.NotificationComponent;

import co.ke.emtechhouse.es.NotificationComponent.TokenNotifications.TokenNotifications;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String title;
    private String subtitle;
    private String message;
    private String click_action;
    private Date DateCreated;
    private Date nextNotificationDate;
    private String icon;
    @JsonIgnore
    private Character firebaseFLag = 'N';



    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;
    @Enumerated(EnumType.STRING)
    private NotificationFrequency notificationFrequency;
    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationStatus;
    @Enumerated(EnumType.STRING)
    private NotificationCategory notificationCategory;

    @Column(name = "isActive")
    private boolean isActive = true;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    List<TokenNotifications> tokenNotifications=new ArrayList<>();

}

