package co.ke.emtechhouse.es.Subscriptions;


import co.ke.emtechhouse.es.Auth.Members.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SubscriptionsRepo extends JpaRepository<Subscriptions, Long> {

    @Transactional
    @Query(nativeQuery = true,value = "SELECT * FROM subscriptions WHERE member_number =:memberNumber")
    Subscriptions searchByMemberNumber(@Param(value = "memberNumber") String memberNumber);

}
