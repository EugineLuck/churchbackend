package co.ke.emtechhouse.es.Deanery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DeaneryRepository extends JpaRepository<Deanery, Long> {

//    List<Deanery> findByDioceseId(long parseLong);
}
