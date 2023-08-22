package co.ke.emtechhouse.es.Auth.Members;
import co.ke.emtechhouse.es.AppUser.AppUser;
import co.ke.emtechhouse.es.Auth.Roles.RoleRepository;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.utils.CONSTANTS;
import co.ke.emtechhouse.es.utils.HttpInterceptor.UserRequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MembersService {
    @Autowired
    private MembersRepository membersRepository;

//    @Autowired
//    MemberUpdateDTO memberUpdateDTO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    private final Date today = new Date();

    public Members membersRegistration(Members members) {
        try {
            Members savedMember = membersRepository.save(members);
            return savedMember;
        } catch (Exception e) {
            log.error("Error occurred while saving member: {}", e.getMessage());
            throw new RuntimeException("Error occurred while saving member");
        }
    }

//    public Members userRegistration(Members user){
//        roleRepository.saveAll(user.getRoles());
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        return membersRepository.save(user);
//    }


    public Members updateMember(Members members){
        return membersRepository.save(members);
    }

    public List<Members> getAllUsers(){
        return membersRepository.findAll();
    }
    public List<Members> allRoleMember(){return membersRepository.allRoleMember();
    }
    public Members getUser(Long id) {
        return membersRepository.findById(id).orElse(null);
    }
 public Members getMember(Long appId) {
        return membersRepository.findByAppId(appId).orElse(null);
    }

    public List<Members> undeletedUsers(){
        return membersRepository.findByDeletedFlag('N');
    }


    public List<Members> findAll() {
        try {
            List<Members> members = membersRepository.findAll();
            return members;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }


    public String generateMember() {
        String newMemberNo = "";
        Optional<Members> memberNumber = membersRepository.getMemberNumber();
        // Retrieve the maximum existing member number from the database
        if (memberNumber.isPresent()) {
            log.info("Increment  by 1");
            String memberNo = memberNumber.get().getMemberNumber();
            String suffix = memberNo.substring(2, memberNo.length());
            int suffixNo = Integer.parseInt(suffix);
            String formattedCode = String.format("%05d", suffixNo + 1);
            newMemberNo = "EM" + formattedCode;
        } else {
            newMemberNo = "EM00001";
        }
        return newMemberNo;

    }



//    public ApiResponse<Members> updateMember(String memberNumber, MemberDetails details) {
//        try {
//            ApiResponse response = new ApiResponse<>();
//            List<Members> member = membersRepository.searchByMemberNumbers(memberNumber);
//            if (member.isPresent()) {
//                Members existingMember = member.get();
//
//
//
//
////                existingMember.se(memberUpdateDTO.getCommunityId());
////                existingMember.setEmail(memberUpdateDTO.getEmail());
//////              existingMember.setGroups(memberUpdateDTO.getGroups());
////                existingMember.setOutStationId(memberUpdateDTO.getOutStationId());
////                existingMember.setPhoneNumber(memberUpdateDTO.getPhoneNumber());
//
//
//
//                Members savedMember = membersRepository.save(existingMember);
//                response.setMessage("Member with Member Number " + existingMember.getMemberNumber() + " updated successfully");
//                response.setStatusCode(HttpStatus.OK.value());
//                response.setEntity(savedMember);
//
//                return response;
//            } else {
//                response.setMessage("Member Not found");
//                response.setStatusCode(HttpStatus.NOT_FOUND.value());
//            }
//
//
//            return response;
//        } catch (Exception e) {
//            log.info("Catched Error {}" + e);
//            return null;
//        }
//
//    }






    

}
