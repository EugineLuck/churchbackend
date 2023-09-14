package co.ke.emtechhouse.es.Announcements;

import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;

import co.ke.emtechhouse.es.AppUser.AppUser;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;

import co.ke.emtechhouse.es.NotificationComponent.NotificationDTO;
import co.ke.emtechhouse.es.NotificationComponent.NotificationService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/Announcements")
public class AnnouncementsController {
    @Autowired
    AnnouncementsService announcementsService;

    @Autowired AnnouncementsRepo announcementsRepo;

    @Autowired
    AnnouncementsRepo announcementsRepo;

    @Autowired
    NotificationService notificationService;

    public AnnouncementsController(){
    }
    @PostMapping("/add")
    public ResponseEntity<Object> addAnnouncements(@RequestBody Announcements announcements) {
        try {
            Announcements savedAnnouncements = announcementsService.saveAnnouncements(announcements);


//            Send Notification
            NotificationDTO notif = new NotificationDTO();
            notif.setMessage(announcements.getMessage());
            notif.setTitle("Announcement");
            notif.setNotificationtype("All");
            notif.setSubtitle(announcements.getAnnouncementDate());
            notificationService.CreateServiceNotificationAll(notif);

            return new ResponseEntity<>(savedAnnouncements, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }
    @GetMapping("/get/all")
    public ResponseEntity<Object> getAllAnnouncements() {
        try {
            List<Announcements> allAnnouncements = announcementsService.findAll();
            return new ResponseEntity<>(allAnnouncements, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }
    @GetMapping("/get/by/id")
    public ResponseEntity<Object> getByIdAnnouncements(Long id) {
        try {
            Announcements allAnnouncements = announcementsService.findById(id);
            return new ResponseEntity<>(allAnnouncements, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateAnnouncement(@PathVariable Long id) {
        ApiResponse response = new ApiResponse<>();
        try {
            Optional<Announcements> announcement = announcementsRepo.findById(id);
            if(announcement.isPresent()){
                Announcements existing = announcement.get();
                existing.setActive(false);
                announcementsRepo.save(existing);
            }
            response.setMessage("Updated");
            response.setStatusCode(201);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }
   

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteAnnouncement(@PathVariable Long id) {
        ApiResponse response = new ApiResponse();
        Optional<Announcements> announcements = announcementsRepo.findById(id);

        if (announcements.isPresent()) {
            Announcements announcements1 = announcements.get();

            announcementsRepo.deleteById(id);

            response.setMessage(" Deleted successfully!");
            response.setStatusCode(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Announcement not found");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        }
    }
}
