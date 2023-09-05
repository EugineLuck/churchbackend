package co.ke.emtechhouse.es.Subscriptions;



import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.Subscribers.Subscibers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SubscriptionService {
    @Autowired
    private SubscriptionsRepo subscriptionsRepo;

    public Subscriptions saveSubscription(Subscriptions sub){

        try {
            Subscriptions saveSub = subscriptionsRepo.save(sub);
            return saveSub;
        }catch (Exception e) {
            System.out.println("Catched Error {} " + e);
            return null;
        }
    }

    public ApiResponse<?> getAll() {

        try {
            ApiResponse response=new ApiResponse<>();
            List<Subscriptions> subs = subscriptionsRepo.findAll();
            if (subs.size()>0) {
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(subs);
            } else {
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }
            return response;
        }catch (Exception e) {
            System.out.println("Catched Error {} " + e);
            return null;
        }

    }


    public Subscriptions findByMemberNumbers(String memberNumber) {
        try {
            Subscriptions savedSub = subscriptionsRepo.searchByMemberNumber(memberNumber);
            return savedSub;
        }catch (Exception e) {
            System.out.println("Catched Error {} " + e);
            return null;
        }

    }




    public void  delete(Long id) {
        try {
            subscriptionsRepo.deleteById(id);
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
        }

    }

    public Subscriptions searchById(Long subscriberId) {
        try {
            Subscriptions savedSubs = subscriptionsRepo.searchById(subscriberId);
            return savedSubs;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }




//    Initialize stk Push



}
