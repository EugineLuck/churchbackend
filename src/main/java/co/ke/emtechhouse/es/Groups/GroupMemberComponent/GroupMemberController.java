package co.ke.emtechhouse.es.Groups.GroupMemberComponent;

import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Auth.Members.MembersRepository;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/group/member")
public class GroupMemberController {

    @Autowired
    GroupMemberRepo groupMemberRepo;

    @Autowired
    MembersRepository membersRepository;

    @GetMapping("/get/by/memberNumber/{memberNumber}")
    public ResponseEntity<?> fetchGroupMemberByMemberNumber(@PathVariable("memberNumber") String memberNumber) {
        try {
            Optional<Members> existingMember = membersRepository.findByMemberNumber(memberNumber);

            if (existingMember.isPresent()) {
                Members member = existingMember.get();

                List<GroupMember> groupMembers = groupMemberRepo.findByMember(member);

                return new ResponseEntity<>(groupMembers, HttpStatus.OK);
            } else {
                ApiResponse response = new ApiResponse();
                response.setMessage("Member not found");
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            log.error("Caught Error: " + e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


