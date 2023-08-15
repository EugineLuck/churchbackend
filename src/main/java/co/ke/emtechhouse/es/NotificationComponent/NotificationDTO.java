package co.ke.emtechhouse.es.NotificationComponent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationDTO {
    private String message;
    private String notificationtype;
    private String title;
    private String subtitle;


}
