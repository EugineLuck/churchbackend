package co.ke.emtechhouse.es.Giving;

import co.ke.emtechhouse.es.Auth.utils.CONSTANTS;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.NotificationComponent.NotificationService;
import co.ke.emtechhouse.es.NotificationComponent.NotificationsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class GivingService {
    @Autowired
    private GivingRepo givingRepo;

    @Autowired
    private NotificationService notificationService;

    public ApiResponse<?> addGiving(Giving giving) {
        try {
            ApiResponse response= new ApiResponse<>();
            giving.setPostedFlag(CONSTANTS.YES);
            giving.setPostedTime(new Date());
            Giving savedGiving = givingRepo.save(giving);
            response.setMessage(HttpStatus.CREATED.getReasonPhrase());
            response.setMessage("GIVING " + giving.getGivingTitle() + " CREATED SUCCESSFULLY AT " + giving.getPostedTime());
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setEntity(savedGiving);

            NotificationsDTO notif = new NotificationsDTO();
            notif.setMessage(giving.getDescription()+"\n This is scheduled to start from "+ giving.getStartDate()+" and end on "+ giving.getEndDate());
            notif.setTitle(giving.getGivingTitle());
            notif.setSubtitle("Notification");
            notif.setNotificationType("All");
            notificationService.CreateServiceNotificationAll(notif);

            return response;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }
    public Giving findById(Long id) {
        try {
            Giving savedGiving = givingRepo.findById(id).get();
            return savedGiving;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }
    public ApiResponse<?> getAllGiving() {
        try {
            ApiResponse response=new ApiResponse<>();
            List<Giving> givings= givingRepo.findAll();
            if (givings.size()>0) {
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(givings);
            } else {
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }
            return response;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    } public ApiResponse<?> getActiveGiving() {
        try {
            ApiResponse response=new ApiResponse<>();
            List<Giving> givings= givingRepo.activeGiving();
            if (givings.size()>0) {
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(givings);
            } else {
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }
            return response;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    } public ApiResponse<?> getUpcomingGiving() {
        try {
            ApiResponse response=new ApiResponse<>();
            List<Giving> givings= givingRepo.upcomingGiving();
            if (givings.size()>0) {
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(givings);
            } else {
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }
            return response;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }public ApiResponse<?> getClossedGiving() {
        try {
            ApiResponse response=new ApiResponse<>();
            List<Giving> givings= givingRepo.completedGiving();
            if (givings.size()>0) {
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(givings);
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
    public ApiResponse delete(Long id) {
        ApiResponse response = new ApiResponse();
        try {
            givingRepo.deleteById(id);
            response.setMessage("Giving deleted successfully");
            response.setStatusCode(HttpStatus.OK.value());
            response.setEntity("");
            return response;

        }catch (Exception e) {
            log.info("Catched Error {} " + e);
        }

        return response;
    }
}
