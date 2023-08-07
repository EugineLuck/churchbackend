package co.ke.emtechhouse.es.Family.FamilyMember;

import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.Family.Family;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/FamilyMember")
public class FamilyMemberController {
    @Autowired
    private FamilyMemberService familyMemberService;
    @Autowired
    private FamilyMemberRepo repo;
    public FamilyMemberController (){
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addFamilyMember(@RequestBody FamilyMember familyMember) {
        try {
            ApiResponse<?> savedFamilyMember = familyMemberService.addFamilyMember(familyMember);
            return new ResponseEntity<>(savedFamilyMember, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }
    @GetMapping("/get/all")
    public ResponseEntity<?> fetchAll() {
        try{
            ApiResponse response = familyMemberService.getAllFamilyMembers();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    @GetMapping("/get/by/id")
    public ResponseEntity<Object> getByIdFamilyMember(Long id) {
        try {
            FamilyMember allFamilyMember = familyMemberService.findById(id);
            return new ResponseEntity<>(allFamilyMember, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }



    @PutMapping("/delete/temp/")
    public ResponseEntity<Object> delete(Long id) {
        try {
            familyMemberService.delete(id);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
        return null;
    }
}
