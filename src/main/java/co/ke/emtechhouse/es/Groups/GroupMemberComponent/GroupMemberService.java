package co.ke.emtechhouse.es.Groups.GroupMemberComponent;

import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Auth.Members.MembersRepository;
import co.ke.emtechhouse.es.Auth.utils.CONSTANTS;
import co.ke.emtechhouse.es.Auth.utils.HttpInterceptor.UserRequestContext;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.Community.Community;
import co.ke.emtechhouse.es.Family.Family;
import co.ke.emtechhouse.es.Groups.Groups;
import co.ke.emtechhouse.es.Groups.GroupsRepo;
import co.ke.emtechhouse.es.OutStation.OutStation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class GroupMemberService {
    @Autowired
    GroupMemberRepo groupMemberRepo;
    @Autowired
    MembersRepository membersRepository;

    @Autowired
    GroupsRepo groupsRepo;

    public ApiResponse<?> addGroup(long memberId,Long groupId) {
        try {
            ApiResponse response= new ApiResponse<>();
//            GroupMember.setStatus("Active");
            Groups groups = groupsRepo.findById(groupId).get();
            Members members = membersRepository.findById(memberId).get();

            GroupMember groupMember = new GroupMember();
            groupMember.setMember(members);
            groupMember.setGroup(groups);

            groupMember.setStatus("Active");

           GroupMember saveGroups= groupMemberRepo.save(groupMember);
            response.setMessage(HttpStatus.CREATED.getReasonPhrase());

            response.setStatusCode(HttpStatus.CREATED.value());
            response.setEntity(saveGroups);
            return response;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }

    public GroupMember findById(Long id) {
        try {
            GroupMember savedGroups = groupMemberRepo.findById(id).get();
            return savedGroups;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }

    public ApiResponse<?> getAllGroups() {
        try {
            ApiResponse response= new ApiResponse<>();
            List<GroupMember> groups= groupMemberRepo.findAll();
            if (groups.size()>0) {
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(groups);
            } else {
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }
            return response;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }

//    public ApiResponse<GroupMember> getGroupByMemberId(Long memberId) {
//        try {
//            ApiResponse response = new ApiResponse();
//            Optional<Members> members = membersRepository.findById(memberId);
//            if (members.isPresent()) {
//                Members presentMembers = members.get();
//                System.out.println(memberId);
//                List<GroupMember> groupMembers = groupMemberRepo.findByMemberIddAndStatus(members.get().getId(), "Active");
//                if (groupMembers.size() > 0) {
//                    response.setMessage(HttpStatus.FOUND.getReasonPhrase());
//                    response.setStatusCode(HttpStatus.FOUND.value());
//                    response.setEntity(groupMembers);
//                } else {
//                    response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
//                    response.setStatusCode(HttpStatus.NOT_FOUND.value());
//                }
//            } else {
//                response.setMessage("group not found");
//                response.setStatusCode(HttpStatus.NOT_FOUND.value());
//            }
//
//            return response;
//        } catch (Exception e) {
//            log.info("Catched Error {} " + e);
//            return null;
//        }
//    }



}
