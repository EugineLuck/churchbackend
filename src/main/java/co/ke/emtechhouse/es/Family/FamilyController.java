package co.ke.emtechhouse.es.Family;

import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.Family.FamilyMember.FamilyMember;
import co.ke.emtechhouse.es.Family.FamilyMember.FamilyMemberRepo;
import co.ke.emtechhouse.es.Groups.Groups;
import co.ke.emtechhouse.es.utils.Responses.EntityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/Family")
public class FamilyController {
    @Autowired
    private FamilyService familyService;
    @Autowired
    private FamilyRepository familyRepository;
    @Autowired
    private FamilyRepository repo;
    @Autowired
    private FamilyMemberRepo familyMemberRepo;
    public FamilyController(){
    }
    @GetMapping("/get/by/familyId/{familyId}")
    public ResponseEntity<?> fetchByFamilyId(@PathVariable("familyId") Long familyId) {
        try {
            ApiResponse response = familyService.findByFamilyId(familyId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @PostMapping("/add")
    public ResponseEntity<Object> addFamily(@RequestBody Family family) {
        try {
            ApiResponse<?> savedFamily = familyService.addFamily(family);
            return new ResponseEntity<>(savedFamily, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }
    @GetMapping("/get/all")
    public ResponseEntity<?> fetchAll() {
        try{
            ApiResponse response = familyService.getAllFamily();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @GetMapping("/fetch/families")
    public ResponseEntity<?> getAllFamilies() {
        try{
            return new ResponseEntity<>(repo.getAllFamilies(), HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
//    @GetMapping("/family/{familyId}/members")
//    public ResponseEntity<List<Members>> getAllMembersByFamily(@PathVariable Long familyId){
//        try{
//            List<Members> members = familyService.getAllMembersByFamilyId(familyId);
//            return ResponseEntity.ok(members);
//        }catch (Exception e) {
//            log.info("Error" + e);
//            return null;
//        }
//    }


    @GetMapping("/get/by/id")
    public ResponseEntity<Object> getBy(Long id) {
        try {
            Family allFamily = familyService.findById(id);
            return new ResponseEntity<>(allFamily, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }
    @DeleteMapping("/family/{id}")
    public ApiResponse<?> deleteFamily(@PathVariable Long id) {
        try {
            familyService.deleteById(id);
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

    @GetMapping("/{memberNumber}")
    public ResponseEntity<FamilyMember> getFamilyMemberByMemberNumber(@PathVariable String memberNumber) {
        FamilyMember familyMember = familyMemberRepo.findByMemberNumber(memberNumber);

        if (familyMember != null) {
            return ResponseEntity.ok(familyMember);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getbyNId")
    public ResponseEntity<EntityResponse<Family>> getFamilyByNid(@RequestParam("nationalID") String nationalID) {
        try{
            return ResponseEntity.ok(familyService.findBynID(nationalID));
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
