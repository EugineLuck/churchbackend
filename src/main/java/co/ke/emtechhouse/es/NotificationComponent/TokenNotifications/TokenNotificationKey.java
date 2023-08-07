package co.ke.emtechhouse.es.NotificationComponent.TokenNotifications;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenNotificationKey implements Serializable {
        private Long token;
        private Long notification;
}
