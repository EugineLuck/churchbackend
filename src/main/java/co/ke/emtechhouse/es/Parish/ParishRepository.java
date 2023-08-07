package co.ke.emtechhouse.es.Parish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParishRepository extends JpaRepository<Parish ,Long>{
//    List<Parish> findByDeaneryId(Long id);
}
