package co.ke.emtechhouse.es.Groups;

import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/Groups")
public class GroupsController {
    @Autowired
    private GroupsService groupsService;
 @Autowired
    private GroupsRepo repo;

    public GroupsController(){
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addGroups(@RequestBody Groups groups) {
        try {
            ApiResponse<?> savedGroups = groupsService.addGroups(groups);
            return new ResponseEntity<>(savedGroups, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }
        @GetMapping("/get/all")
    public ResponseEntity<?> fetchAll() {
        try{
            ApiResponse response = groupsService.getAllGroups();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @GetMapping("/get/getAllGroups")
    public ResponseEntity<?> getAllGroups() {
        try{
            return new ResponseEntity<>(repo.getAllGroups(), HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @GetMapping("/get/outstationPerGroup")
    public ResponseEntity<?> getOutstation() {
        try{
            return new ResponseEntity<>(repo.getOutStation(), HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    @GetMapping("/member/{groupId}")
    public ApiResponse getMembers(@PathVariable Long groupId) {
        ApiResponse response = new ApiResponse<>();
        try{
            List<Object[]> membersData = repo.getGroupMembers(groupId);
            List<Members> allMembers = new ArrayList<>();
            for (Object[] data : membersData) {
                Members member = new Members();
                member.setMemberNumber((String) data[0]);
                member.setLastName((String) data[1]);
                member.setFirstName((String) data[2]);
                allMembers.add(member);
            }

            response.setEntity(allMembers);
            return  response;
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    @GetMapping("/get/by/outStationId/{outStationId}")
    public ResponseEntity<?> fetchGroupsByOutStationId(@PathVariable("outStationId") Long outStationId) {
        try {
            ApiResponse response = groupsService.getGroupsByOutStationId(outStationId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @GetMapping("/get/by/id")
    public ResponseEntity<Object> getByIdGroups(Long id) {
        try {
            Groups allGroups = groupsService.findById(id);
            return new ResponseEntity<>(allGroups, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }



//    @GetMapping("/groups/{groupId}/members")
//    public ResponseEntity<List<Members>> getAllMembersByGroup(@PathVariable Long groupId){
//        try{
//            List<Members> members = groupsService.getAllMembersByGroup(groupId);
//            return ResponseEntity.ok(members);
//        }catch (Exception e) {
//            log.info("Error" + e);
//            return null;
//        }
//    }


    @DeleteMapping("/groups/{id}")
    public ApiResponse<?> deleteGroups(@PathVariable Long id) {
        try {
            groupsService.deleteById(id);
            ApiResponse<?> response = new ApiResponse<>();
            response.setMessage("Group deleted successfully");
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
    @PostMapping("/process-groups")
    public String processGroups(@RequestParam("selectedGroups") List<String> selectedGroups) {
        // Process the selected groups
        for (String group : selectedGroups) {
            System.out.println("Selected group: " + group);
            // Perform the desired actions for each selected group
        }

        // Redirect or return a response
        return "redirect:/success";
    }
}
