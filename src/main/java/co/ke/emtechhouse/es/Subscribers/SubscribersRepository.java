package co.ke.emtechhouse.es.Subscribers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SubscribersRepository extends JpaRepository<Subscibers, Long> {
    @Transactional
    @Query(nativeQuery = true,value = "SELECT * FROM subscribers WHERE member_number =:memberNumber")
    Subscibers searchByMemberNumber(@Param(value = "memberNumber") String memberNumber);

    Subscibers findBymemberNumber(String memberNumber);

    @Transactional
    @Query(nativeQuery = true,value = "SELECT * FROM subscribers WHERE id =:id")
    Subscibers searchById(@Param(value = "id") Long id);


    @Transactional
    @Query(nativeQuery = true,value = "SELECT * FROM subscribers WHERE id =:id")
    List <Subscibers> searchByItemId(@Param(value = "id") Long id);

    @Transactional
    @Query(nativeQuery = true,value = "SELECT * FROM subscribers WHERE subscription_id =:id")
    List <Subscibers> searchBySubscriptionItemId(@Param(value = "id") Long id);

    List findBySubscriptionItemId(Long id);

    Optional<Subscibers> findByMemberNumber(String memberNumber);
}
