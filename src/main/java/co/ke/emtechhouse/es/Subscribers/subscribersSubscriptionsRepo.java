package co.ke.emtechhouse.es.Subscribers;

import co.ke.emtechhouse.es.Subscriptions.Subscriptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface subscribersSubscriptionsRepo extends JpaRepository<subscribersSubscriptions, Long> {

    @Transactional
    @Query(nativeQuery = true,value = "SELECT * FROM subscribers_subscriptions WHERE subscriber_id =:id")
    List<subscribersSubscriptions> searchBySubscriberId(@Param(value = "id") Long id);

    List<subscribersSubscriptions> findBySubscriberId(Long id);

    List<subscribersSubscriptions> findBySubscriptionId(Long subscriptionItemId);
}
