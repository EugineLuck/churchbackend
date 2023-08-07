package co.ke.emtechhouse.es.OutStation;

import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.Community.Community;
import co.ke.emtechhouse.es.Family.Family;
import co.ke.emtechhouse.es.Groups.Groups;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/OutStation")
public class OutStationController {
    @Autowired
    private OutStationService outStationService;
    @Autowired
    private OutStationRepository repo;
    public OutStationController(){
    }
    @PostMapping("/add")
    public ResponseEntity<Object> addOutStation(@RequestBody OutStation outStation) {
        try {
            ApiResponse<?> savedOutStations = outStationService.addOutStation(outStation);
            return new ResponseEntity<>(savedOutStations, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }
    @GetMapping("/get/all/outStationDetails")
    public ResponseEntity<?> fetchAllOutStationDetails() {
        try{

            return new ResponseEntity<>(repo.findStations(), HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    @GetMapping("/fetch/all")
    public ResponseEntity<?> findAll() {
        try{

            return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
//    @GetMapping("/get/all")
//    public ResponseEntity<?> fetchAllOutStations() {
//        try{
//            ApiResponse response = outStationService.getAllOutStations();
//            return new ResponseEntity<>(response, HttpStatus.OK);
//
//        }catch (Exception e){
//            log.info("Catched Error {} " + e);
//            return null;
//        }
//    }
    @GetMapping("/outStation/{outStationId}/members")
    public ResponseEntity<List<Members>> getAllMembersByOutStation(@PathVariable Long outStationId){
        try{
            List<Members> members = outStationService.getAllMembersByOutStationId(outStationId);
            return ResponseEntity.ok(members);
        }catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @GetMapping("/outStation/{outStationId}/communities")
    public ResponseEntity<List<Community>> getAllCommunityByOutStation(@PathVariable Long outStationId){
        try{
            List<Community> communities = outStationService.getAllCommunitiesByOutStationId(outStationId);
            return ResponseEntity.ok(communities);
        }catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @GetMapping("/outStation/{outStationId}/families")
    public ResponseEntity<List<Family>> getAllFamiliesByOutStation(@PathVariable Long outStationId){
        try{
            List<Family> families = outStationService.getAllFamiliesByOutStationId(outStationId);
            return ResponseEntity.ok(families);
        }catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @GetMapping("/get/by/OutStationId")
    public ResponseEntity<Object> getByOutStationId(Long outStationId) {
        try {
            OutStation allOutStation = outStationService.findByOutStationId(outStationId);
            return new ResponseEntity<>(allOutStation, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }
    @DeleteMapping("/outStation/{id}")
    public ApiResponse<?> deleteOutStation(@PathVariable Long id) {
        try {
            outStationService.deleteById(id);
            ApiResponse<?> response = new ApiResponse<>();
            response.setMessage("OutStation deleted successfully");
            response.setStatusCode(HttpStatus.OK.value());
            return response;
        } catch (Exception e) {
            ApiResponse<?> response = new ApiResponse<>();
            response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            log.info("Catched Error {} " + e);
            return response;
        }
    }
}
