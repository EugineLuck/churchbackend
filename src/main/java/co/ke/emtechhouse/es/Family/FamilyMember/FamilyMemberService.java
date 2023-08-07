package co.ke.emtechhouse.es.Family.FamilyMember;

import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FamilyMemberService {
    @Autowired
    private FamilyMemberRepo familyMemberRepo;

    public ApiResponse<?> addFamilyMember(FamilyMember familyMember) {
        try {
            ApiResponse response= new ApiResponse<>();
            familyMember.setPostedTime(new Date());
            familyMember.setFamilyId(familyMember.getFamilyId());
//            family.setPostedBy(UserRequestContext.getCurrentUser());
            FamilyMember savedFamilyMember = familyMemberRepo.save(familyMember);
            System.out.println("new family member" + familyMember);
            response.setMessage(HttpStatus.CREATED.getReasonPhrase());
            response.setMessage("FAMILY MEMEBER" + familyMember.getFamilyMemberFName() + " ADDED SUCCESSFULLY ");
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setEntity(savedFamilyMember);
            return response;

        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }
    public FamilyMember findById(Long id) {
        try {
            FamilyMember savedFamilyMember= familyMemberRepo.findById(id).get();
            return savedFamilyMember;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }
    public ApiResponse<?> getAllFamilyMembers() {
        try {
            ApiResponse response=new ApiResponse<>();
            List<FamilyMember> familyMembers= familyMemberRepo.findAll();
            if (familyMembers.size()>0) {
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(familyMembers);
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
    public void  delete(Long id) {
        try {
            familyMemberRepo.deleteById(id);
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
        }

    }

}
