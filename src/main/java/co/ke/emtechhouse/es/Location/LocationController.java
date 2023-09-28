//package co.ke.emtechhouse.es.Location;
//
//import co.ke.emtechhouse.es.Auth.utils.MigrationData.CountiesJson;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@Slf4j
//@RestController
//@RequestMapping("/getLocations/")
//public class LocationController {
//    public  CountiesJson countiesJson;
//    public LocationController(CountiesJson countiesJson){
//
//        this.countiesJson = countiesJson;
//    }
//
//    @GetMapping("/get/all")
//    public ResponseEntity<Object> getLocatiions() {
//        try {
//            List<County> allCounties = (List<County>) countiesJson;
//            return new ResponseEntity<>(allCounties, HttpStatus.OK);
//        } catch (Exception e) {
//            log.info("Error" + e);
//            return null;
//        }
//    }
//}
