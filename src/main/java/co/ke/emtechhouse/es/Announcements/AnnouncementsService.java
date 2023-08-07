package co.ke.emtechhouse.es.Announcements;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AnnouncementsService {
    @Autowired AnnouncementsRepo announcementsRepo;

    public Announcements saveAnnouncements(Announcements announcements) {
        try {
            Announcements saveAnnouncements= announcementsRepo.save(announcements);
            return saveAnnouncements;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }
    public Announcements findById(Long id) {
        try {
            Announcements savedAnnouncements= announcementsRepo.findById(id).get();
            return savedAnnouncements;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }
    public List<Announcements> findAll() {
        try {
            List<Announcements> announcements = announcementsRepo.findAll();
            return announcements;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    public void delete (Long id){
        try {
            announcementsRepo.deleteById(id);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
        }

    }
}
