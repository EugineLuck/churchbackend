package co.ke.emtechhouse.es.Giving;

import co.ke.emtechhouse.es.Advertisement.Advertisement;
import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Auth.Requests.GivingRequest;
import co.ke.emtechhouse.es.Auth.Requests.SignupRequest;
import co.ke.emtechhouse.es.Auth.Roles.ERole;
import co.ke.emtechhouse.es.Auth.Roles.Role;
import co.ke.emtechhouse.es.Auth.utils.CONSTANTS;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.Community.Community;
import co.ke.emtechhouse.es.Community.CommunityRepository;
import co.ke.emtechhouse.es.Family.Family;
import co.ke.emtechhouse.es.Family.FamilyRepository;
import co.ke.emtechhouse.es.GivingLevels.GivingLevel;
import co.ke.emtechhouse.es.GivingLevels.GivingLevelRepo;
import co.ke.emtechhouse.es.Groups.GroupMemberComponent.GroupMember;
import co.ke.emtechhouse.es.Groups.Groups;
import co.ke.emtechhouse.es.Groups.GroupsRepo;
import co.ke.emtechhouse.es.NotificationComponent.NotificationDTO;
import co.ke.emtechhouse.es.NotificationComponent.NotificationService;
import co.ke.emtechhouse.es.NotificationComponent.NotificationsDTO;
import co.ke.emtechhouse.es.NotificationComponent.TokenComponent.Token;
import co.ke.emtechhouse.es.OutStation.OutStation;
import co.ke.emtechhouse.es.OutStation.OutStationRepository;
import co.ke.emtechhouse.es.SmsComponent.Emtech.Dtos.Dtos.SmsDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/Giving")
public class GivingController {
    @Autowired
    private GivingService givingService;

    @Autowired
    private GivingRepo givingRepo;

    @Autowired
    private GroupsRepo groupsRepo;

    @Autowired
    GivingLevelRepo givingLevelRepo;
    @Autowired
    FamilyRepository familyRepository;
    @Autowired
    OutStationRepository outStationRepository;

    @Autowired
    CommunityRepository communityRepository;

    @Autowired
    NotificationService notificationService;

    public GivingController() {
    }

    @PostMapping("/add")
    public ResponseEntity<?> addGiving(@Valid @RequestBody GivingRequest  givingRequest) throws MessagingException {
        ApiResponse response = new ApiResponse();

        Giving giv = new Giving();
        giv.setDescription(givingRequest.getDescription());
        giv.setEndDate(givingRequest.getEndDate());
        giv.setGivingLevel(givingRequest.getGivingLevel());
        giv.setGivingTitle(WordUtils.capitalizeFully(givingRequest.getGivingTitle()));
        giv.setStartDate(givingRequest.getStartDate());
        giv.setTargetAmount(givingRequest.getTargetAmount());
        giv.setStatus("Active");
        giv.setPostedFlag(CONSTANTS.YES);
        giv.setPostedTime(new Date());

        Giving saveGiving = givingRepo.save(giv);


//        Check Groups
        List<Long> groupsId = givingRequest.getGroupId();
        if (groupsId != null && !groupsId.isEmpty()) {
            List<GivingLevel> groupsIds = new ArrayList<>();
            for (Long groupId : groupsId) {
                Groups group = groupsRepo.findById(groupId)
                        .orElseThrow(() -> new RuntimeException("Error: Group with id " + groupId + " not found."));

                GivingLevel glevel = new GivingLevel();
                glevel.setGroups(group);
                glevel.setGiving(saveGiving);
                glevel = givingLevelRepo.save(glevel);
                groupsIds.add(glevel);
            }

//            Send Notification
            NotificationDTO notif = new NotificationDTO();
            notif.setMessage(givingRequest.getDescription()+"\n This is scheduled to start from "+ givingRequest.getStartDate()+" and end on "+ givingRequest.getEndDate());
            notif.setTitle(givingRequest.getGivingTitle());
            notif.setSubtitle("Notification");
            notif.setNotificationtype("All");



//            Loop through the groups

            for(GivingLevel groups : groupsIds){
//                for(Groups g: groups.getGroups()){
//
//                }
                System.out.println(groups);
//                notificationService.CreateServiceNotificationAllSelectedGroups(notif, groups.getGroups());

            }





        }

//        Check Families
        List<Long> familyId = givingRequest.getFamilyId();
        if (familyId != null && !familyId.isEmpty()) {
            List<GivingLevel> familyMembers = new ArrayList<>();
            for (Long familyIdx : familyId) {
                Family family = familyRepository.findById(familyIdx)
                        .orElseThrow(() -> new RuntimeException("Error"));

                GivingLevel glevel = new GivingLevel();
                glevel.setFamily(family);
                glevel.setGiving(saveGiving);
                glevel = givingLevelRepo.save(glevel);
                familyMembers.add(glevel);
            }

        }

        //        Check Churches
        List<Long> chucrhId = givingRequest.getChurchId();
        if (familyId != null && !familyId.isEmpty()) {
            List<GivingLevel> churchMembers = new ArrayList<>();
            for (Long churchIdx : chucrhId) {
                OutStation outStation = outStationRepository.findById(churchIdx)
                        .orElseThrow(() -> new RuntimeException("Error"));

                GivingLevel glevel = new GivingLevel();
                glevel.setOutStation(outStation);
                glevel.setGiving(saveGiving);
                glevel = givingLevelRepo.save(glevel);
                churchMembers.add(glevel);
            }

        }

        //        Check Communities
        List<Long> communityId = givingRequest.getCommunityId();
        if (familyId != null && !familyId.isEmpty()) {
            List<GivingLevel> communityIds = new ArrayList<>();
            for (Long community : communityId) {
                Community comm = communityRepository.findById(community)
                        .orElseThrow(() -> new RuntimeException("Error"));

                GivingLevel glevel = new GivingLevel();
                glevel.setCommunity(comm);
                glevel.setGiving(saveGiving);
                glevel = givingLevelRepo.save(glevel);
                communityIds.add(glevel);
            }

        }






        response.setMessage(HttpStatus.CREATED.getReasonPhrase());
        response.setMessage("GIVING " + givingRequest.getGivingTitle() + " CREATED SUCCESSFULLY AT " + givingRequest.getPostedTime());
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setEntity(saveGiving);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @GetMapping("/get/all")
    public ResponseEntity<?> fetchAll() {
        try{
            ApiResponse response = givingService.getAllGiving();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @GetMapping("/get/Active")
    public ResponseEntity<?> fetchActive() {
        try{
            ApiResponse response = givingService.getActiveGiving();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }@GetMapping("/get/Upcoming")
    public ResponseEntity<?> fetchUpcoming() {
        try{
            ApiResponse response = givingService.getUpcomingGiving();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }@GetMapping("/get/Clossed")
    public ResponseEntity<?> fetchClossed() {
        try{
            ApiResponse response = givingService.getClossedGiving();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    @GetMapping("/get/by/id")
    public ResponseEntity<Object> getByIdGiving(Long id) {
        try {
            Giving allGiving = givingService.findById(id);
            return new ResponseEntity<>(allGiving, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @PutMapping("/delete/temp/")
    public ResponseEntity<Object> delete(Long id) {

        try {
            givingService.delete(id);

        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
        return null;
    }
}
