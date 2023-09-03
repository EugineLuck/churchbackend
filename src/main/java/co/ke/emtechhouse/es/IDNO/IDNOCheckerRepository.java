package co.ke.emtechhouse.es.IDNO;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDNOCheckerRepository extends JpaRepository<IDNODetails,Long> {

    Optional<IDNODetails> findByDocumentNumber(String nationalID);
}
