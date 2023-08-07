package co.ke.emtechhouse.es.Community;

import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Auth.Members.MembersRepository;
import co.ke.emtechhouse.es.Auth.utils.CONSTANTS;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.Family.Family;
import co.ke.emtechhouse.es.Family.FamilyRepository;
import co.ke.emtechhouse.es.Groups.Groups;
import co.ke.emtechhouse.es.OutStation.OutStation;
import co.ke.emtechhouse.es.OutStation.OutStationRepository;
import co.ke.emtechhouse.es.utils.HttpInterceptor.UserRequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CommunityService {
    @Autowired
    private CommunityRepository communityRepository;
    @Autowired
    OutStationRepository outStationRepository;
    @Autowired
    private MembersRepository membersRepository;
    @Autowired
    private FamilyRepository familyRepository;


    public ApiResponse<?> addCommunity(Community community) {
        try {
            ApiResponse response= new ApiResponse<>();
            community.setPostedFlag(CONSTANTS.YES);
            community.setPostedTime(new Date());
//            community.setPostedBy(UserRequestContext.getCurrentUser());
            community.setDeletedFlag(CONSTANTS.NO);
            community.setVerifiedFlag(CONSTANTS.NO);
            Community savedCommunity = communityRepository.save(community);
            response.setMessage(HttpStatus.CREATED.getReasonPhrase());
            response.setMessage("COMMUNITY " + community.getCommunityName() + " CREATED SUCCESSFULLY AT " + community.getPostedTime());
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setEntity(savedCommunity);
            return response;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }

    public Community findById(Long id) {
        try {
            Community savedCommunity = communityRepository.findById(id).get();
            return savedCommunity;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }

    public ApiResponse<?> getAllCommunity() {
        try {
            ApiResponse response= new ApiResponse<>();
            List<Community> communities= communityRepository.findAll();
            if (communities.size()>0) {
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(communities);
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

    public ApiResponse<Community> getCommunityByOutStationId(Long outStationId) {
        try {
            ApiResponse response = new ApiResponse();
                Optional<OutStation> outStation = outStationRepository.findById(outStationId);
                if (outStation.isPresent()) {
                    OutStation presentOutStation = outStation.get();
                    System.out.println(outStation);
//                    presentUser.setGroupMembers((List<GroupMember>) groupMemberService.getAllGroupMembers().getEntity());
                    List<Community> communities = communityRepository.findByOutStationId(outStationId);
                    if (communities.size() > 0) {
                        response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                        response.setStatusCode(HttpStatus.FOUND.value());
                        response.setEntity(communities);
                    } else {
                        response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                        response.setStatusCode(HttpStatus.NOT_FOUND.value());
                    }
                } else {
                    response.setMessage("Community not found");
                    response.setStatusCode(HttpStatus.NOT_FOUND.value());
                }

            return response;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    public List<Members> getAllMembersByCommunityId(Long communityId){
        try{
            Community community = communityRepository.findById(communityId)
                    .orElseThrow(() -> new RuntimeException("Community not found"));

            return membersRepository.findByCommunityId(communityId);

        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    public ApiResponse<?> deleteById(Long id) {
        try {
            communityRepository.deleteById(id);
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
    public List<Family> getAllFamiliesByCommunityId(Long communityId){
        try{
            Community community = communityRepository.findById(communityId)
                    .orElseThrow(() -> new RuntimeException(" Community not found"));

            return familyRepository.findByCommunityId(communityId);

        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }



    }

