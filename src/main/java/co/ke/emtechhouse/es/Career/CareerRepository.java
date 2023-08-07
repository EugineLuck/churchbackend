package co.ke.emtechhouse.es.Career;

//import co.ke.emtechhouse.es.Advertisement.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareerRepository extends JpaRepository<Career, Long> {
//
}
