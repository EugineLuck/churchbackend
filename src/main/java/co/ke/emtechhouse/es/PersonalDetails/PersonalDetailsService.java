package co.ke.emtechhouse.es.PersonalDetails;

import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Auth.Members.MembersRepository;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.Family.FamilyMember.FamilyMember;
import co.ke.emtechhouse.es.Family.FamilyMember.FamilyMemberRepo;
import co.ke.emtechhouse.es.USSD.USSDRepo;
import co.ke.emtechhouse.es.utils.CONSTANTS;
import co.ke.emtechhouse.es.utils.HttpInterceptor.UserRequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class PersonalDetailsService {
    @Autowired
    private PersonalDetailsRepo personalDetailsRepo;
    @Autowired
    USSDRepo ussdRepo;
    @Autowired
    MembersRepository membersRepository;
    @Autowired
    FamilyMemberRepo familyMemberRepo;

    public PersonalDetails findById(Long id) {
        try {
            PersonalDetails savedDetails= personalDetailsRepo.findById(id).get();
            return savedDetails;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }



    public void  delete(Long id) {
        try {
            personalDetailsRepo.deleteById(id);
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
        }

    }





    public List<PersonalDetails> findAll() {
        try {
            List<PersonalDetails> personalDetails= personalDetailsRepo.findAll();
            return personalDetails;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }
}
