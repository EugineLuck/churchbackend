package co.ke.emtechhouse.es.Community;

import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.Family.Family;
import co.ke.emtechhouse.es.Groups.Groups;
import co.ke.emtechhouse.es.OutStation.OutStation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/Community")
public class CommunityController {
    @Autowired
    private CommunityService communityService;
    @Autowired
    private CommunityRepository repo;
    public CommunityController(){
    }
    @PostMapping("/add")
    public ResponseEntity<Object> addCommunity(@RequestBody Community community) {
        try {
            ApiResponse<?> savedCommunity = communityService.addCommunity(community);
            return new ResponseEntity<>(savedCommunity, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }
    @GetMapping("/fetch/communities")
    public ResponseEntity<?> getAllcommunities() {
        try{
            return new ResponseEntity<>(repo.getAllCommunity(), HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @GetMapping("/get/all")
    public ResponseEntity<?> fetchAll() {
        try{
            ApiResponse response = communityService.getAllCommunity();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }


    @GetMapping("/get/by/id")
    public ResponseEntity<Object> getByIdCommunity(@RequestParam  Long id) {
        try {
            Community allCommunity = communityService.findById(id);
            return new ResponseEntity<>(allCommunity, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }




    @DeleteMapping("/communities/{id}")
    public ApiResponse<?> deleteCommunity(@PathVariable Long id) {
        try {
            communityService.deleteById(id);
            ApiResponse<?> response = new ApiResponse<>();
            response.setMessage("Community deleted successfully");
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
    @GetMapping("/community/{communityId}/members")
    public ResponseEntity<List<Members>> getAllMembersByCommunity(@PathVariable Long communityId){
        try{
            List<Members> members = communityService.getAllMembersByCommunityId(communityId);
            return ResponseEntity.ok(members);
        }catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }
    @GetMapping("/get/getAllByOustationId/{id}")
    public ResponseEntity<?> getAllByOustationId(@PathVariable Long id) {
        try{
            return new ResponseEntity<>(repo.getAllCommunitiesOutstation(id), HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    @GetMapping("/community/{communityId}/families")
    public ResponseEntity<List<Family>> getAllFamiliesByCommunity(@PathVariable Long communityId){
        try{
            List<Family> families = communityService.getAllFamiliesByCommunityId(communityId);
            return ResponseEntity.ok(families);
        }catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

}
