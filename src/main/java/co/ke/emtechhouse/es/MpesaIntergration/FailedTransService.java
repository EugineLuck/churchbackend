package co.ke.emtechhouse.es.MpesaIntergration;

import co.ke.emtechhouse.es.Auth.Members.MembersRepository;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.Giving.GivingRepo;
import co.ke.emtechhouse.es.NotificationComponent.NotificationRepo;
import co.ke.emtechhouse.es.NotificationComponent.NotificationService;
import co.ke.emtechhouse.es.NotificationComponent.TokenComponent.TokenRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FailedTransService {
    @Autowired
    FailedRepo failedRepo;
    @Autowired
    MembersRepository membersRepository;


    @Autowired
    GivingRepo givingRepo;


    @Autowired
    TokenRepo tokenRepo;
    @Autowired
    NotificationRepo notificationRepo;
    @Autowired
    NotificationService notificationService;


    @Autowired
    MpesaCallerService mpesaCallerService;


    public ApiResponse<?> getAllTransactions() {
        ApiResponse response = new ApiResponse();
        try {

            List<FailedTransactions> transactions = failedRepo.findAll();
            if (transactions.size() > 0) {
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(transactions);
            } else {
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }
//            }
            return response;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    public ApiResponse<?> getAllFailedTransactions() {
        ApiResponse response = new ApiResponse();
        try {

            List<FailedReport> failedReports = failedRepo.getAllFailedTransactions();
            if (failedReports.size() > 0) {
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(failedReports);
            } else {
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }
//            }
            return response;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    public ApiResponse<FailedTransactions> getTransactionsByGivingId(Long givingId) {
        ApiResponse response = new ApiResponse();
        try {

            Optional<FailedTransactions> FailedTransactions = failedRepo.findById(givingId);
            if (FailedTransactions.isPresent()) {
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(FailedTransactions);
            } else {
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }
//            }
            return response;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    public ApiResponse<FailedTransactions> getTransactionsByPhoneNumber(String phoneNumber) {
        ApiResponse response = new ApiResponse();
        try {

            Optional<FailedTransactions> FailedTransactions = failedRepo.findByPhoneNumber(phoneNumber);
            if (FailedTransactions.isPresent()) {
                response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.FOUND.value());
                response.setEntity(FailedTransactions);
            } else {
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }
//            }
            return response;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }


    public ApiResponse getTransactionsByMemberNumber(String memberNumber) {
        ApiResponse apiResponse = new ApiResponse<>();
        try {
            Optional<FailedTransactions> transactions = failedRepo.findByMemberNumber(memberNumber);
            apiResponse.setEntity(transactions);
            apiResponse.setMessage("List of transactions ");
            apiResponse.setStatusCode(200);
        } catch (Exception e) {
            apiResponse.setMessage("Error fetching transactions");
            apiResponse.setStatusCode(404);
        }
        return apiResponse;
    }


}
