package co.ke.emtechhouse.es.Events;

import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.Family.Family;
import co.ke.emtechhouse.es.Giving.Giving;
import co.ke.emtechhouse.es.Parish.Parish;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ApiResponse<?> getActiveEvents() {
        try {
            ApiResponse response=new ApiResponse<>();
            List<Events> events= eventsRepo.activeEvents();
            if (events.size()>0) {
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(events);
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
    public ApiResponse<?> getUpcomingActivities() {
        try {
            ApiResponse response=new ApiResponse<>();
            List<Events> events= eventsRepo.upcomingEvents();
            if (events.size()>0) {
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(events);
            } else {
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }
            return response;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }public ApiResponse<?> getClossedActivities() {
        try {
            ApiResponse response=new ApiResponse<>();
            List<Events> events= eventsRepo.completedEvents();
            if (events.size()>0) {
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(events);
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
}
