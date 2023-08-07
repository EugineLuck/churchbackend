package co.ke.emtechhouse.es.Diocese;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DioceseRepository extends JpaRepository<Diocese, Long> {

    @Transactional
    @Query(nativeQuery = true, value = "SELECT id, diocese_name FROM diocese")
    List<Diocese> getDiocese(Long id, String diocese_name);
}
