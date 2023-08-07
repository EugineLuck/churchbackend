package co.ke.emtechhouse.es.NotificationComponent;//package co.ke.emtechhouse.es.NotificationComponent;
//
//import co.ke.emtechhouse.es.Auth.Users.Users;
//import co.ke.emtechhouse.es.Auth.Users.UsersRepository;
//import co.ke.emtechhouse.es.FamilyComponent.Family;
//import co.ke.emtechhouse.es.FamilyComponent.FamilyRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Optional;
//
//@Component
//public class NotificationJob {
//
//    @Autowired
//    private UsersRepository userRepository;
//    @Autowired
//    private FamilyRepo familyRepo;
//
//    @Scheduled(cron = "0 0 8 * * *") // Runs every day at 8 AM
//    public void sendNotifications() {
//        // Retrieve users who have given consent but not added to a family
//        List<Users> users = userRepository.findByConsentGivenAndFamilyIdIsNull(true);
//
//        for (Users user : users) {
//            // Send notification to the user (e.g., via email, SMS, or push notification)
//            sendNotification(user);
//
//            Optional<Family> family=familyRepo.findByUsersFk(user.getId());
//            if (family.isPresent()){
//                Family family1=family.get();
//                family1.getId();
//                family1.setUsersFk(user.getId());
//                userRepository.save(user);
//            }
//
//        }
//    }
//
//    private void sendNotification(Users user) {
//    }
//}
