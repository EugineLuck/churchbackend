package co.ke.emtechhouse.es.OutStation;

import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Auth.Members.MembersRepository;
import co.ke.emtechhouse.es.Auth.utils.CONSTANTS;
import co.ke.emtechhouse.es.Auth.utils.HttpInterceptor.UserRequestContext;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.Community.Community;
import co.ke.emtechhouse.es.Community.CommunityRepository;
import co.ke.emtechhouse.es.Family.Family;
import co.ke.emtechhouse.es.Family.FamilyRepository;
import co.ke.emtechhouse.es.Groups.Groups;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class OutStationService {
    @Autowired
    private OutStationRepository outStationRepository;
    @Autowired
    private MembersRepository membersRepository;
    @Autowired
    private CommunityRepository communityRepository;
    @Autowired
    private FamilyRepository familyRepository;


    public ApiResponse<?> addOutStation(OutStation outStation) {
        try {
            ApiResponse response= new ApiResponse<>();
            outStation.setPostedFlag(CONSTANTS.YES);
            outStation.setPostedTime(new Date());
//            outStation.setPostedBy(UserRequestContext.getCurrentUser());
            outStation.setDeletedFlag(CONSTANTS.NO);
            outStation.setVerifiedFlag(CONSTANTS.NO);
            OutStation savedOutStation = outStationRepository.save(outStation);
            response.setMessage(HttpStatus.CREATED.getReasonPhrase());
            response.setMessage("OUTSTATION " + outStation.getOutStationName() + " CREATED SUCCESSFULLY AT " + outStation.getPostedTime());
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setEntity(savedOutStation);
            return response;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }
    public OutStation findByOutStationId(Long outStationId) {
        try {
            OutStation savedOutStation= outStationRepository.findById(outStationId).get();
            return savedOutStation;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }
    public ApiResponse<?> getAllOutStationDetails() {
        try {
            ApiResponse response=new ApiResponse<>();
            List<OutStation> outStations= outStationRepository.findAll();
            if (outStations.size()>0) {
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(outStations);
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
    public List<Members> getAllMembersByOutStationId(Long outStationId){
        try{
            OutStation outStation = outStationRepository.findById(outStationId)
                    .orElseThrow(() -> new RuntimeException("OutStation not found"));

            return membersRepository.findByOutStationId(outStationId);

        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    public List<Community> getAllCommunitiesByOutStationId(Long outStationId){
        try{
            OutStation outStation = outStationRepository.findById(outStationId)
                    .orElseThrow(() -> new RuntimeException("OutStation not found"));

            return communityRepository.findByOutStationId(outStationId);

        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    public List<Family> getAllFamiliesByOutStationId(Long outStationId){
        try{
            OutStation outStation = outStationRepository.findById(outStationId)
                    .orElseThrow(() -> new RuntimeException("OutStation not found"));

            return familyRepository.findByOutStationId(outStationId);

        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    public ApiResponse<?> getAllOutStations() {
        try {
            ApiResponse response= new ApiResponse<>();
            List<OutStation> outStations= outStationRepository.findAll();
            if (outStations.size()>0) {
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(outStations);
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
            outStationRepository.deleteById(id);
            ApiResponse<?> response = new ApiResponse<>();
            response.setMessage("OutStation deleted successfully");
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
}
