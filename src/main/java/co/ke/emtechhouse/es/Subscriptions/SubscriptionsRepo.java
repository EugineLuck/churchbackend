package co.ke.emtechhouse.es.Subscriptions;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SubscriptionsRepo extends JpaRepository<Subscriptions, Long> {

    @Transactional
    @Query(nativeQuery = true,value = "SELECT * FROM subscriptions WHERE member_number =:memberNumber")
    Subscriptions searchByMemberNumber(@Param(value = "memberNumber") String memberNumber);


    Optional<Subscriptions> findBymemberNumber(String memberNumber);


//    @Transactional
//    @Query(nativeQuery = true,value = "SELECT * FROM subscriptions WHERE id =:id")
//    List<Subscriptions> getAllById(@Param(value = "memberNumber") String memberNumber);

    @Transactional
    @Query(nativeQuery = true,value = "SELECT * FROM subscriptions WHERE id =:id")
    List<Subscriptions> searchById(@Param(value = "id") Long id);



//    Subscriptions searchByMemberNumber(String memberNumber);
}
