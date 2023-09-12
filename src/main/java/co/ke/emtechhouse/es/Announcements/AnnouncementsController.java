package co.ke.emtechhouse.es.Announcements;

import co.ke.emtechhouse.es.NotificationComponent.NotificationDTO;
import co.ke.emtechhouse.es.NotificationComponent.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/Announcements")
public class AnnouncementsController {
    @Autowired
    AnnouncementsService announcementsService;

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
    @PutMapping("/delete/temp/")
    public ResponseEntity<Object> delete(Long id) {
        try {
            announcementsService.delete(id);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
        return null;
    }
}
