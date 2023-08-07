package co.ke.emtechhouse.es.Diocese;

import co.ke.emtechhouse.es.Family.Family;
import co.ke.emtechhouse.es.Family.FamilyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/Diocese")
public class DioceseController {

    @Autowired
    private DioceseService dioceseService;
    public DioceseController(){
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addFamily(@RequestBody Diocese diocese) {
        try {
            Diocese savedDiocese = dioceseService.saveDiocese(diocese);
            return new ResponseEntity<>(savedDiocese, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }
    @GetMapping("/get/all")
    public ResponseEntity<Object> getAllDiocese() {
        try {
            List<Diocese> allDiocese= dioceseService.findAll();
            return new ResponseEntity<>(allDiocese, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }
    @GetMapping("/get/by/id")
    public ResponseEntity<Object> getByIdDiocese(Long id) {
        try {
            Diocese allDiocese = dioceseService.findById(id);
            return new ResponseEntity<>(allDiocese, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }
    @PutMapping("/delete/temp/")
    public ResponseEntity<Object> delete(Long id) {
        try {
            dioceseService.delete(id);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
        return null;
    }

}
