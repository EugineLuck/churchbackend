package co.ke.emtechhouse.es.Programs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ProgramsRepo extends JpaRepository<Programs, Long> {

}

