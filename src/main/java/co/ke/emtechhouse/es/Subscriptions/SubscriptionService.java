package co.ke.emtechhouse.es.Subscriptions;



import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Subscriptions> getAll() {
        try {
            List<Subscriptions> subs = subscriptionsRepo.findAll();
            return subs;
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








}
