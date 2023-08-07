package co.ke.emtechhouse.es.USSD;

import co.ke.emtechhouse.es.Auth.Members.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface USSDRepo extends JpaRepository<USSD, Long> {
    Optional<USSD> findByPhoneNumber(String phoneNumber);
    @Query(value = "SELECT  `first_name`, `phone_number` FROM `ussd` ", nativeQuery = true)
    List<FirstName> getFirstNameAndPhone();
    interface FirstName{
        public String getFirst_name();
        public String getPhone_number();
    }
    @Query(value = "SELECT  * FROM `members` order by id desc limit 1 ", nativeQuery = true)
    Optional<Members> getMemberNumber();
}
