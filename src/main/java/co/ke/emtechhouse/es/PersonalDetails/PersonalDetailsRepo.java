package co.ke.emtechhouse.es.PersonalDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalDetailsRepo extends JpaRepository<co.ke.emtechhouse.es.PersonalDetails.PersonalDetails, Long> {
    Optional<co.ke.emtechhouse.es.PersonalDetails.PersonalDetails> findByMemberNumber(String memberNumber);


//    Optional<co.ke.emtechhouse.es.PersonalDetails.PersonalDetails> findByUserPhone(String user_phone);
}
