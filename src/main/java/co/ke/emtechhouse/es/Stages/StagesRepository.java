package co.ke.emtechhouse.es.Stages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StagesRepository extends JpaRepository<Stages,Long> {
    List<Stages> findByMemberNumber(String memberNumber);
}
