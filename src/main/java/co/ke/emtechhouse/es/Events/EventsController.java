package co.ke.emtechhouse.es.Events;

import co.ke.emtechhouse.es.Auth.Requests.ActivityRequest;
import co.ke.emtechhouse.es.Auth.Requests.GivingRequest;
import co.ke.emtechhouse.es.Auth.utils.CONSTANTS;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.Community.Community;
import co.ke.emtechhouse.es.Community.CommunityRepository;
import co.ke.emtechhouse.es.Family.Family;
import co.ke.emtechhouse.es.Family.FamilyRepository;
import co.ke.emtechhouse.es.Giving.Giving;
import co.ke.emtechhouse.es.GivingLevels.GivingLevel;
import co.ke.emtechhouse.es.GivingLevels.GivingLevelRepo;
import co.ke.emtechhouse.es.Groups.Groups;
import co.ke.emtechhouse.es.Groups.GroupsRepo;
import co.ke.emtechhouse.es.NotificationComponent.NotificationDTO;
import co.ke.emtechhouse.es.NotificationComponent.NotificationService;
import co.ke.emtechhouse.es.OutStation.OutStation;
import co.ke.emtechhouse.es.OutStation.OutStationRepository;
import co.ke.emtechhouse.es.Parish.Parish;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/Events")
public class EventsController {
    @Autowired
    private EventsService eventsService;
    @Autowired
    EventsRepo eventsRepo;
    @Autowired
    ParticipantsRepo participantsRepo;
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
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    LocalDateTime now = LocalDateTime.now();

    public EventsController(){
    }
    @PostMapping("/add")
    public ResponseEntity<?> addactivity(@Valid @RequestBody ActivityRequest  activityRequest) throws MessagingException {
        ApiResponse response = new ApiResponse();

        Events events = new Events();
        events.setDescription(activityRequest.getDescription());
        events.setParticipants(activityRequest.getParticipants());

        events.setRequirements(activityRequest.getRequirements());
        events.setEventName(WordUtils.capitalizeFully(activityRequest.getEventName()));
        events.setEventDate(activityRequest.getEventDate());
        events.setDatePosted(dtf.format(now));

        events.setStatus(activityRequest.getStatus());


        Events saveActivity = eventsRepo.save(events);

        response.setMessage("Event Added");
        response.setEntity(saveActivity);
        response.setStatusCode(HttpStatus.CREATED.value());

        //Send Notification
        NotificationDTO notif = new NotificationDTO();
        notif.setMessage(activityRequest.getDescription()+"\n This is scheduled to start from "+ activityRequest.getEventDate());
        notif.setTitle(activityRequest.getEventName());
        notif.setSubtitle("Notification");
        notif.setNotificationtype("All");

//Send notification for all Members
        if(activityRequest.getGroupsId() == null && activityRequest.getFamilyId() == null && activityRequest.getChurchId() == null && activityRequest.getCommunityId() == null){
            notificationService.CreateServiceNotificationAll(notif);
        }


        List<Long> groupsId = activityRequest.getGroupsId();
        if (groupsId != null && !groupsId.isEmpty()) {
            List<Groups> groupData = new ArrayList<>();
            for (Long groupId : groupsId) {
                Groups group = groupsRepo.findById(groupId)
                        .orElseThrow(() -> new RuntimeException("Error: Group with id " + groupId + " not found."));

                Participants plevel = new Participants();
                plevel.setGroups(group);
                plevel.setEvents(saveActivity);
                plevel = participantsRepo.save(plevel);
                groupData.add(plevel.getGroups());
            }
            //Loop through the groups
            for (Groups groupDetails : groupData){
                notificationService.CreateServiceNotificationAllSelectedGroups(notif, groupDetails);
            }

        }

//        Send Families
        List<Long> familyId = activityRequest.getFamilyId();
        if (familyId != null && !familyId.isEmpty()) {
            List<Family> familyMembers = new ArrayList<>();
            for (Long familyIdx : familyId) {
                Family family = familyRepository.findById(familyIdx)
                        .orElseThrow(() -> new RuntimeException("Error"));

                Participants plevel = new Participants();
                plevel.setFamily(family);
                plevel.setEvents(saveActivity);
                plevel = participantsRepo.save(plevel);
                familyMembers.add(plevel.getFamily());
            }
            for (Family familyDetails : familyMembers){
                notificationService.CreateServiceNotificationAllSelectedFamilies(notif, familyDetails);
            }
        }


//                Send Churches
        List<Long> chucrhId = activityRequest.getChurchId();
        if (chucrhId != null && !chucrhId.isEmpty()) {
            List<OutStation> churchMembers = new ArrayList<>();
            for (Long churchIdx : chucrhId) {
                OutStation outStation = outStationRepository.findById(churchIdx)
                        .orElseThrow(() -> new RuntimeException("Error"));

                Participants plevel = new Participants();
                plevel.setOutStation(outStation);
                plevel.setEvents(saveActivity);
                plevel = participantsRepo.save(plevel);
                churchMembers.add(plevel.getOutStation());
            }
            for (OutStation churchData : churchMembers){
                notificationService.CreateServiceNotificationAllSelectedOutstation(notif, churchData);
            }
        }

        //        Send Communities
        List<Long> communityId = activityRequest.getCommunityId();
        if (communityId != null && !communityId.isEmpty()) {
            List<Community> communityDetails = new ArrayList<>();
            for (Long community : communityId) {
                Community comm = communityRepository.findById(community)
                        .orElseThrow(() -> new RuntimeException("Error"));

                Participants level = new Participants();
                level.setCommunity(comm);
                level.setEvents(saveActivity);
                level = participantsRepo.save(level);
                communityDetails.add(level.getCommunity());
            }

            for (Community communityData : communityDetails){
                notificationService.CreateServiceNotificationAllSelectedCommunity(notif, communityData);
            }

        }

        response.setMessage(HttpStatus.CREATED.getReasonPhrase());
        response.setMessage("EVENT " + activityRequest.getEventName() + " HAS BEEN CREATED SUCCESSFULLY AT " + dtf.format(now));
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setEntity(saveActivity);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
    @GetMapping("/get/Active")
    public ResponseEntity<?> fetchActive() {
        try{
            ApiResponse response = eventsService.getActiveEvents();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }@GetMapping("/get/Upcoming")
    public ResponseEntity<?> fetchUpcoming() {
        try{
            ApiResponse response = eventsService.getUpcomingActivities();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }@GetMapping("/get/Clossed")
    public ResponseEntity<?> fetchClossed() {
        try{
            ApiResponse response = eventsService.getClossedActivities();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    @GetMapping("/get/by/id")
    public ResponseEntity<Object> getByIdGiving(Long id) {
        try {
            Events allEvents = eventsService.findById(id);
            return new ResponseEntity<>(allEvents, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse> deleteEvent(@PathVariable Long id) {
        ApiResponse response = new ApiResponse();
        Optional<Events> events = eventsRepo.findById(id);

        if (events.isPresent()) {
            Events events1 = events.get();

            List<Participants> participants = participantsRepo.findByEvents(events);
            participantsRepo.deleteAll(participants);


            eventsRepo.deleteEvent( events1.getId());

            response.setMessage("Event Deleted successfully!");
            response.setStatusCode(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Event not found");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


}
