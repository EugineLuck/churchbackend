package co.ke.emtechhouse.es.NotificationComponent;

import co.ke.emtechhouse.es.GoalComponent.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Long> {

    @Query(value = "SELECT * FROM notification JOIN token_notifications WHERE token_notifications.token_id = :tokenId", countQuery = "select 1", nativeQuery = true)
    List<Notification> findByTokenId(Long tokenId);

    @Query(value = "SELECT * FROM `notification` WHERE `firebaseflag` = 'Y'", countQuery = "select 1", nativeQuery = true)
    List<Notification> findSentNotifications();

    @Query(value = "SELECT * FROM `notification` WHERE nextPaymentDate(`next_payment_date`)<=:date", countQuery = "select 1", nativeQuery = true)
    List<Goal> findAllGoalsByDate(Date date);
//    @Query(value = "SELECT * FROM ")

    @Query(value = "SELECT * FROM `notification` WHERE date(`next_notification_date`)<=:date", countQuery = "select 1", nativeQuery = true)
    List<Notification> findNotificationsByDate(Date date);

    Optional<Notification> findByNotificationCategory(NotificationCategory notificationCategory);

    @Query(value = "SELECT * FROM `notification` WHERE notification_status = :notificationStatus AND expiry_date != :expiryDate", countQuery = "select 1", nativeQuery = true)
    List<Notification> findByNotificationStatusAndExpiryDate(NotificationStatus notificationStatus, Date expiryDate);

    List<Notification> findByNotificationStatus(NotificationStatus notificationStatus);

//    List<Notification> findByCategory(NotificationCategory notificationCategory);
}
