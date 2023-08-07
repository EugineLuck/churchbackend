package co.ke.emtechhouse.es.Deanery;

import co.ke.emtechhouse.es.Diocese.Diocese;
import co.ke.emtechhouse.es.Diocese.DioceseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DeaneryService {

    @Autowired
    private DeaneryRepository deaneryRepository;
    public Deanery saveDeanery(Deanery deanery) {
        try {
            Deanery savedDeanery= deaneryRepository.save(deanery);
            return savedDeanery;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }
    public Deanery findById(Long id) {
        try {
            Deanery savedDeanery= deaneryRepository.findById(id).get();
            return savedDeanery;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }
    public List<Deanery> findAll() {
        try {
            List<Deanery> deanery= deaneryRepository.findAll();
            return deanery;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }
    public void  delete(Long id) {
        try {
            deaneryRepository.deleteById(id);
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
        }

    }


}
