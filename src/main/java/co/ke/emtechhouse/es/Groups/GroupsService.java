package co.ke.emtechhouse.es.Groups;

import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Auth.Members.MembersRepository;
import co.ke.emtechhouse.es.Auth.utils.CONSTANTS;
import co.ke.emtechhouse.es.Auth.utils.HttpInterceptor.UserRequestContext;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.BankComponent.Bank;
import co.ke.emtechhouse.es.Community.Community;
import co.ke.emtechhouse.es.OutStation.OutStation;
import co.ke.emtechhouse.es.OutStation.OutStationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class GroupsService {
    @Autowired
    private GroupsRepo groupsRepo;
    @Autowired
    OutStationRepository outStationRepository;
    @Autowired
    private MembersRepository membersRepository;

    public ApiResponse<?> addGroups(Groups groups) {
        try {
            ApiResponse response= new ApiResponse<>();
                groups.setPostedFlag(CONSTANTS.YES);
                groups.setPostedTime(new Date());
//                groups.setPostedBy(UserRequestContext.getCurrentUser());
                groups.setDeletedFlag(CONSTANTS.NO);
                groups.setVerifiedFlag(CONSTANTS.NO);
                Groups savedGroups = groupsRepo.save(groups);
                response.setMessage(HttpStatus.CREATED.getReasonPhrase());
                response.setMessage("GROUPS " + groups.getGroupName() + " CREATED SUCCESSFULLY AT " + groups.getPostedTime());
                response.setStatusCode(HttpStatus.CREATED.value());
                response.setEntity(savedGroups);
            return response;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }
    public Groups findById(Long id) {
        try {
            Groups savedGroups= groupsRepo.findById(id).get();
            return savedGroups;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }
    public ApiResponse<Groups> getGroupsByOutStationId(Long outStationId) {
        try {
            ApiResponse response = new ApiResponse();
            Optional<OutStation> outStation = outStationRepository.findById(outStationId);
            if (outStation.isPresent()) {
                OutStation presentOutStation = outStation.get();
                System.out.println(outStation);

                List<Groups> groups = groupsRepo.findByOutStationId(outStationId);
                if (groups.size() > 0) {
                    response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                    response.setStatusCode(HttpStatus.FOUND.value());
                    response.setEntity(groups);
                } else {
                    response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                    response.setStatusCode(HttpStatus.NOT_FOUND.value());
                }
            } else {
                response.setMessage("Groups not found");
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }

            return response;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    public ApiResponse<?> getAllGroups() {
        try {
            ApiResponse response=new ApiResponse<>();
            List<Groups> groups= groupsRepo.findAll();
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

    } public ApiResponse<?> getOutstation() {
        try {
            ApiResponse response=new ApiResponse<>();
            List<GroupDetails> groups= groupsRepo.getOutStation();
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
    public ApiResponse<?> deleteById(Long id) {
        try {
            groupsRepo.deleteById(id);
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
//    public List<Members> getAllMembersByGroup(Long groupId){
//        try{
//            Groups groups = groupsRepo.findById(groupId)
//                    .orElseThrow(() -> new RuntimeException("Group not found"));
//
//            return membersRepository.findByGroups(groupId);
//
//        }catch (Exception e){
//            log.info("Catched Error {} " + e);
//            return null;
//        }
//    }
}
