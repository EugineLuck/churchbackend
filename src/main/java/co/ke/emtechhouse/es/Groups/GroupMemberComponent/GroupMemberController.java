//package co.ke.emtechhouse.es.Groups.GroupMemberComponent;
//
//import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@Slf4j
//@RestController
//@RequestMapping(path = "api/v1/group/member")
//public class GroupMemberController {
//
//    @Autowired
//    GroupMemberService groupMemberService;
//    @PostMapping("/add/{groupId}/{memberNumber}")
//    public ResponseEntity<?> newGroupMember(@RequestBody GroupMember groupMember, @PathVariable Long groupId, @PathVariable String memberNumber) {
//        try{
//            ApiResponse response = groupMemberService.addGroup(groupMember, groupId, memberNumber);
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        }catch (Exception e){
//            log.info("Catched Error {} " + e);
//            return null;
//        }
//    }
//    @PutMapping("/verify/{id}")
//    public ResponseEntity<?> verifyGroupMember(@PathVariable Long id) {
//        try{
//            ApiResponse response = groupMemberService.verifyGroupMember(id);
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        }catch (Exception e){
//            log.info("Catched Error {} " + e);
//            return null;
//        }
//    }
//    @GetMapping("/get/all")
//    public ResponseEntity<?> fetchAll() {
//        try{
//            ApiResponse response = groupMemberService.getAllGroupMembers();
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        }catch (Exception e){
//            log.info("Catched Error {} " + e);
//            return null;
//        }
//    }
//    @GetMapping("/get/by/id/{groupMemberId}")
//    public ResponseEntity<?> fetchGroupMemberById(@PathVariable("groupMemberId") Long groupMemberId) {
//        try{
//            ApiResponse response = groupMemberService.getGroupMemberById(groupMemberId);
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        }catch (Exception e){
//            log.info("Catched Error {} " + e);
//            return null;
//        }
//    }
//
//    @GetMapping("/get/by/groupId/{groupId}")
//    public ResponseEntity<?> getGroupMembersByGroupId(@PathVariable("groupId") Long groupId) {
//        try{
//            ApiResponse response =groupMemberService.getGroupMembersByGroupFk(groupId);
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        }catch (Exception e){
//            log.info("Catched Error {} " + e);
//            return null;
//        }
//    }
//    @GetMapping("/get/group/member/details/by/groupId/{groupId}")
//    public ResponseEntity<?> getGroupMemberDetailsByGroupFk(@PathVariable("groupId") Long groupId) {
//        try{
//            ApiResponse response =groupMemberService.getGroupMemberDetailsByGroupFk(groupId);
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        }catch (Exception e){
//            log.info("Catched Error {} " + e);
//            return null;
//        }
//    }
//    @GetMapping("/get/group/member/by/group_member_status")
//    public ResponseEntity<?> getGroupMembersByGroupMemberStatus(@RequestBody GroupMemberStatus groupMemberStatus) {
//        try{
//            ApiResponse response =groupMemberService.getGroupMembersByGroupMemberStatus(groupMemberStatus);
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        }catch (Exception e){
//            log.info("Catched Error {} " + e);
//            return null;
//        }
//    }
//    @PutMapping("/update")
//    public ResponseEntity<?> updateGroupMember(@RequestBody GroupMember groupMember) {
//        try{
//            ApiResponse response = groupMemberService.updateGroupMember(groupMember);
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        }catch (Exception e){
//            log.info("Catched Error {} " + e);
//            return null;
//        }
//    }
//
//    @PutMapping("/delete/temp/{groupMemberId}")
//    public ResponseEntity<?> deleteGroupMember(@PathVariable("groupMemberId") Long groupMemberId) {
//        try{
//            ApiResponse response = groupMemberService.tempDeleteGroupMember(groupMemberId);
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        }catch (Exception e){
//            log.info("Catched Error {} " + e);
//            return null;
//        }
//    }
//}
