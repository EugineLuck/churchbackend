package co.ke.emtechhouse.es.NotificationComponent;

import lombok.Data;

import java.util.List;
@Data
public class NotificationRequest {
        private Notification notification;
        private List<String> memberNumbers;

        private String groupId;

        public List<String> getmemberNumbers() {
                return memberNumbers;
        }
}

