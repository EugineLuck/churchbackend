package co.ke.emtechhouse.es.Subscribers;


import co.ke.emtechhouse.es.Subscriptions.Subscriptions;
import co.ke.emtechhouse.es.Subscriptions.SubscriptionsRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SubscribersService {
    @Autowired
    private SubscribersRepository subscribersRepository;
    @Autowired
    private SubscriptionsRepo subscriptionsRepo;


    //find the subscription by id
    //get subscribers list
    //add new subscriber
//    public Subscibers addSubscriber(Long subscriptionId, Subscibers sub){
//        try {
//            Optional<Subscriptions> subscriptionsOptional = subscriptionsRepo.findById(subscriptionId);
//            Subscriptions subscriptions = subscriptionsOptional.get();
////            subscriptions.get
////            Optional<Subscri>
//            Subscibers saveSub = subscribersRepository.save(sub);
//            return saveSub;
//        }catch (Exception e) {
//            log.info("Catched Error {} " + e);
//            return null;
//        }
//    }
    public Subscibers saveSubscriber(Subscibers sub){
        try {
            Subscibers saveSub = subscribersRepository.save(sub);
            return saveSub;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }


    public List<Subscibers> getAll() {
        try {
            List<Subscibers> subs = subscribersRepository.findAll();
            return subs;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }

    public Subscibers findSubscriptionsByMemberNumber(String memberNumber) {
        try {
            Subscibers savedSubs = subscribersRepository.findBymemberNumber(memberNumber);
            return savedSubs;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }

    public void  delete(Long id) {
        try {
            subscribersRepository.deleteById(id);
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
        }

    }







}
