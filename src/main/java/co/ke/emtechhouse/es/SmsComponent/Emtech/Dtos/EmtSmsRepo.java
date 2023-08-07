package co.ke.emtechhouse.es.SmsComponent.Emtech.Dtos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmtSmsRepo extends JpaRepository<EmtSms, Long> {
}
