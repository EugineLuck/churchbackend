package co.ke.emtechhouse.es.Diocese;

import co.ke.emtechhouse.es.Family.Family;
import co.ke.emtechhouse.es.Family.FamilyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DioceseService {

    @Autowired
    private DioceseRepository dioceseRepository;
    public Diocese saveDiocese(Diocese diocese) {
        try {
            Diocese savedDiocese= dioceseRepository.save(diocese);
            return savedDiocese;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }
    public Diocese findById(Long id) {
        try {
            Diocese savedDiocese= dioceseRepository.findById(id).get();
            return savedDiocese;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }
    public List<Diocese> findAll() {
        try {
            List<Diocese> diocese= dioceseRepository.findAll();
            return diocese;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }
    public void  delete(Long id) {
        try {
            dioceseRepository.deleteById(id);
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
        }

    }
}
