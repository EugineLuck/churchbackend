package co.ke.emtechhouse.es.Family;

import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Auth.Members.MembersRepository;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.Community.Community;
import co.ke.emtechhouse.es.Family.FamilyMember.FamilyMember;
import co.ke.emtechhouse.es.Family.FamilyMember.FamilyMemberRepo;
import co.ke.emtechhouse.es.Family.FamilyMember.FamilyMemberRole;
import co.ke.emtechhouse.es.Groups.Groups;
import co.ke.emtechhouse.es.OutStation.OutStation;
import co.ke.emtechhouse.es.utils.CONSTANTS;
import co.ke.emtechhouse.es.utils.HttpInterceptor.UserRequestContext;
import co.ke.emtechhouse.es.utils.Responses.EntityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FamilyService {
    @Autowired
    MembersRepository membersRepository;
    @Autowired
    private FamilyRepository familyRepository;
    @Autowired
    private FamilyMemberRepo familyMemberRepo;

    public ApiResponse<?> addFamily(Family family) {
        try {
            String familyNumber = generateFamily();
            ApiResponse response= new ApiResponse<>();
            family.setPostedTime(new Date());
//            family.setPostedBy(UserRequestContext.getCurrentUser());
            family.setFamilyNumber(familyNumber);
            Family savedFamily = familyRepository.save(family);
            response.setMessage(HttpStatus.CREATED.getReasonPhrase());
            response.setMessage("FAMILY " + family.getFamilyName() + " CREATED SUCCESSFULLY AT " +  " FAMILY NUMBER " + family.getFamilyNumber());
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setEntity(savedFamily);
            return response;

        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }




    public Family findById(Long id) {
        try {
            Family savedFamily= familyRepository.findById(id).get();
            return savedFamily;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }
    public EntityResponse<Family> findBynID(@Param("nationalID") String nationalID) {
        try {
            EntityResponse entityResponse = new EntityResponse<>();
            Optional<Family> optionalFamily = familyRepository.getFamilyByNId(nationalID);
            if(optionalFamily.isPresent()){
               entityResponse.setMessage("Found");
               entityResponse.setEntity(optionalFamily.get());
               entityResponse.setStatusCode(HttpStatus.OK.value());
            }else {
                entityResponse.setMessage("Not found");
                entityResponse.setEntity(null);
                entityResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            }
            return entityResponse;
        } catch (Exception e) {
            e.printStackTrace();
            // Log the actual error message instead of the generic "Catched Error {}" message
            log.error("Error fetching family by nationalID: " + nationalID, e);
            return null;
        }
    }
    public ApiResponse<?> getAllFamily() {
        try {
            ApiResponse response=new ApiResponse<>();
            List<Family> family= familyRepository.findAll();
            if (family.size()>0) {
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(family);
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


//    public List<Members> getAllMembersByFamilyId(Long familyId){
//        try{
//            Family family = familyRepository.findById(familyId)
//                    .orElseThrow(() -> new RuntimeException("Family not found"));
//
//            return membersRepository.findByFamilyId(familyId);
//
//        }catch (Exception e){
//            log.info("Catched Error {} " + e);
//            return null;
//        }
//    }
public ApiResponse<?> deleteById(Long id) {
    try {
        familyRepository.deleteById(id);
        ApiResponse<?> response = new ApiResponse<>();
        response.setMessage("Family deleted successfully");
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

    public FamilyMember getFamilyMemberByMemberNumber(String memberNumber) {
        return familyMemberRepo.findByMemberNumber(memberNumber);
    }


    public String generateFamily() {
        String newFamilyNo = "";
        Optional<Family> familyNumber = familyRepository.getFamilyNumber();
        // Retrieve the maximum existing family number from the database
        if (familyNumber.isPresent()) {
            log.info("Increment  by 1");
            String familyNo = familyNumber.get().getFamilyNumber();
            String suffix = familyNo.substring(2, familyNo.length());
            int suffixNo = Integer.parseInt(suffix);
            String formattedCode = String.format("%05d", suffixNo + 1);
            newFamilyNo = "FN" + formattedCode;
        } else {
            newFamilyNo = "FN00001";
        }
        return newFamilyNo;

    }
    public ApiResponse<Family> findByFamilyId(Long familyId) {
        try {
            ApiResponse response = new ApiResponse();
            Optional<Members> members = membersRepository.findByFamilyId(familyId);
            if (members.isPresent()) {
                Members presentMembers= members.get();
                System.out.println(members);
//                    presentUser.setGroupMembers((List<GroupMember>) groupMemberService.getAllGroupMembers().getEntity());
                List<Family> families = familyRepository.findByOutStationId(familyId);
                if (families.size() > 0) {
                    response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                    response.setStatusCode(HttpStatus.FOUND.value());
                    response.setEntity(families);
                } else {
                    response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                    response.setStatusCode(HttpStatus.NOT_FOUND.value());
                }
            } else {
                response.setMessage("Family not found");
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }

            return response;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }
}
