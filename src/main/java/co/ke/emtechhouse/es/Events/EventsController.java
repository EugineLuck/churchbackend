package co.ke.emtechhouse.es.Events;

import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.Family.Family;
import co.ke.emtechhouse.es.Parish.Parish;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public EventsController(){
    }
    @PostMapping("/add")
    public ResponseEntity<Object> addEvents(@RequestBody Events events) {
        try {
            events.setDatePosted(new Date());
            events.setStatus(true);
            Events saveEvents = eventsRepo.save(events);
            return new ResponseEntity<>(saveEvents, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }
    @GetMapping("/fetch")
    public ResponseEntity<Object> getAllEvents() {
        try {
            Events saveEvents = eventsRepo.fetchEvents();
            return new ResponseEntity<>(saveEvents, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<Object> getEvent(@PathVariable Long id) {
        ApiResponse response =new ApiResponse();
        try {
            Optional<Events> existing = eventsRepo.findById(id);
            if(existing.isPresent()){
                response.setStatusCode(200);
                response.setEntity(existing.get());
                response.setMessage("Found");
            }else{
                response.setStatusCode(404);
                response.setMessage("No Event Found with id");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<Object> updateEvent(@PathVariable Long id, @RequestBody Events events) {
//        ApiResponse response = new ApiResponse<>();
//        try {
//            Optional<Events> existing = eventsRepo.findById(id);
//            if(existing.isPresent()){
//                existing.set
//                response.setStatusCode(200);
//                response.setMessage("Found");
//            }else{
//                response.setStatusCode(404);
//                response.setMessage("No Event Found with id");
//            }
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            log.info("Error" + e);
//            return null;
//        }
//    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteEvent(@PathVariable Long id) {
        ApiResponse response = new ApiResponse<>();
        try {
            eventsRepo.deleteById(id);
            response.setMessage("Deleted");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }



}
