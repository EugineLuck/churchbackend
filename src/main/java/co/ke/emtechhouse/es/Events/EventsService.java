package co.ke.emtechhouse.es.Events;

import co.ke.emtechhouse.es.Family.Family;
import co.ke.emtechhouse.es.Parish.Parish;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EventsService {
    @Autowired
    private EventsRepo eventsRepo;

    public Events saveEvents(Events events) {
        try {
            Events saveEvents= eventsRepo.save(events);
            return saveEvents;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }
    public Events findById(Long id) {
        try {
            Events savedEvents= eventsRepo.findById(id).get();
            return savedEvents;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }
    public List<Events> findAll() {
        try {
            List<Events> events = eventsRepo.findAll();
            return events;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }
}
