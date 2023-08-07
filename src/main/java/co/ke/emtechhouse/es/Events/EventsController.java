package co.ke.emtechhouse.es.Events;

import co.ke.emtechhouse.es.Family.Family;
import co.ke.emtechhouse.es.Parish.Parish;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/Events")
public class EventsController {
    @Autowired
    private EventsService eventsService;
    public EventsController(){
    }
    @PostMapping("/add")
    public ResponseEntity<Object> addEvents(@RequestBody Events events) {
        try {
            Events saveEvents = eventsService.saveEvents(events);
            return new ResponseEntity<>(saveEvents, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }
}
