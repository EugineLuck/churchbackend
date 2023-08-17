package co.ke.emtechhouse.es.Subscribers;

import co.ke.emtechhouse.es.Subscriptions.Subscriptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SubscribersRepository extends JpaRepository<Subscibers, Long> {
    @Transactional
    @Query(nativeQuery = true,value = "SELECT * FROM subscribers WHERE member_number =:memberNumber")
    Subscibers searchByMemberNumber(@Param(value = "memberNumber") String memberNumber);

    Subscibers findBymemberNumber(String memberNumber);

}
