package co.ke.emtechhouse.es.NotificationComponent;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationsDTO {
    private String title;
    private String subtitle;
    private String message;
    private String notificationType;
}
