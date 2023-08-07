package co.ke.emtechhouse.es.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdsRepo extends JpaRepository<Advertisement, Long> {

//    List<Advertisement> findByDioceseId(long parseLong);

}
