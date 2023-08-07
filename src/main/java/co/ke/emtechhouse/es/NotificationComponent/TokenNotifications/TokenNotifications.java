package co.ke.emtechhouse.es.NotificationComponent.TokenNotifications;

import co.ke.emtechhouse.es.NotificationComponent.Notification;
import co.ke.emtechhouse.es.NotificationComponent.TokenComponent.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TokenNotifications {
    @EmbeddedId
    TokenNotificationKey tokenNotificationKey;

    @ManyToOne
    @MapsId("token")
    @JoinColumn(name = "token_id")
    private Token token;

    @ManyToOne
    @MapsId("notification")
    @JoinColumn(name = "notification_id")
    private Notification notification;
}
