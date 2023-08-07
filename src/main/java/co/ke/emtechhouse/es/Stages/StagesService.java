package co.ke.emtechhouse.es.Stages;

import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Auth.Members.MembersRepository;
import co.ke.emtechhouse.es.Auth.utils.CONSTANTS;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.Community.Community;
import co.ke.emtechhouse.es.OutStation.OutStation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StagesService {
    @Autowired
    private StagesRepository stagesRepository;
    @Autowired
    private MembersRepository membersRepository;

    public ApiResponse<?> addStages(Stages stages) {
        try {
            ApiResponse response= new ApiResponse<>();
            stages.setPostedFlag(CONSTANTS.YES);
            stages.setPostedTime(new Date());
            stages.setMemberNumber(stages.getMemberNumber());
            stages.setDeletedFlag(CONSTANTS.NO);
            stages.setVerifiedFlag(CONSTANTS.NO);
            Stages savedStages = stagesRepository.save(stages);
            response.setMessage(HttpStatus.CREATED.getReasonPhrase());
            response.setMessage("STAGES " + stages.getStageName() + " CREATED SUCCESSFULLY AT " + stages.getPostedTime());
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setEntity(savedStages);
            return response;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }
    public Stages findById(Long id) {
        try {
            Stages savedStages = stagesRepository.findById(id).get();
            return savedStages;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }
    public ApiResponse<?> getAllStages() {
        try {
            ApiResponse response= new ApiResponse<>();
            List<Stages> stages= stagesRepository.findAll();
            if (stages.size()>0) {
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(stages);
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

    public ApiResponse<Stages> getStagesByMemberNumber(String memberNumber) {
        try {
            ApiResponse response = new ApiResponse();
            Optional<Members> member = membersRepository.findByMemberNumber(memberNumber);
            if (member.isPresent()) {
                Members presentMembers = member.get();
                System.out.println(member);

                List<Stages> stages = stagesRepository.findByMemberNumber(memberNumber);
                if (stages.size() > 0) {
                    response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                    response.setStatusCode(HttpStatus.FOUND.value());
                    response.setEntity(stages);
                } else {
                    response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                    response.setStatusCode(HttpStatus.NOT_FOUND.value());
                }
            } else {
                response.setMessage("MemberDetails not found");
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }

            return response;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    public void delete (Long id){
        try {
            stagesRepository.deleteById(id);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
        }

    }
}
