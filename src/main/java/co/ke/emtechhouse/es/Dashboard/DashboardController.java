package co.ke.emtechhouse.es.Dashboard;

import co.ke.emtechhouse.es.Advertisement.Advertisement;
import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Auth.Members.MembersRepository;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {
    @Autowired
    MembersRepository membersRepository;

    public DashboardController() {

    }

    @GetMapping("/params")
    public ResponseEntity<Object> getAllDashboardParams() {
        ApiResponse response = new ApiResponse<>();
        try {
            response.setMessage("Dashboard Params");
            List<Members> allMembers = membersRepository.findAll();
            Map<String, Integer> monthCounts = new HashMap<>();

            // Define a date format to parse the string date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            // Initialize a list of all month names
            String[] allMonthNames = new SimpleDateFormat("MMMM", Locale.ENGLISH).format(new Date()).split(" ");

            for (Members member : allMembers) {
                String dateAsString = member.getPostedTime(); // Assuming getPostedTime() returns the string date

                try {
                    Date registrationDate = dateFormat.parse(dateAsString);

                    String monthName = new SimpleDateFormat("MMMM", Locale.ENGLISH).format(registrationDate); // Extract the full month name

                    // Update the count for the corresponding month
                    monthCounts.put(monthName, monthCounts.getOrDefault(monthName, 0) + 1);
                } catch (ParseException e) {
                    // Handle parsing errors if necessary
                    e.printStackTrace();
                }
            }

            // Fill in missing months with zero counts
            for (String monthName : allMonthNames) {
                monthCounts.putIfAbsent(monthName, 0);
            }

            // Sort the map by month name
            LinkedHashMap<String, Integer> sortedMonthCounts = new LinkedHashMap<>();
            monthCounts.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEachOrdered(x -> sortedMonthCounts.put(x.getKey(), x.getValue()));

            response.setStatusCode(HttpStatus.OK.value());
            response.setEntity(sortedMonthCounts);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error: " + e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("An error occurred");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
