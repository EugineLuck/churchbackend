package co.ke.emtechhouse.es.MpesaIntergration;



import co.ke.emtechhouse.es.Auth.utils.DatesCalculator;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;


import co.ke.emtechhouse.es.DTO.MpesaExpress.MpesaExpressDTO.MpesaexpresscallbackDTO;

import co.ke.emtechhouse.es.MpesaIntergration.Mpesa_Express.StkPushStatusResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.criteria.internal.expression.SubqueryComparisonModifierExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/transaction")
public class  TransactionController {

    @Autowired
    TransactionService transactionService;
    @Autowired
    TransactionRepo transactionRepo;

    @Autowired
    private DatesCalculator datesCalculator;
    @RequestMapping(path = "api/v1/transaction")



        @PostMapping("/add")
        public ResponseEntity<?> newTransaction(@RequestBody Transaction transaction) {
            try{
                ApiResponse response = transactionService.enter2(transaction);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }catch (Exception e){
                log.info("Catched Error {} " + e);
                return null;
            }
        }
    @PostMapping("/callback")
    public ResponseEntity<?> mpesaExpressCallback(@RequestBody StkPushStatusResponse stkPushStatusResponse) {
        try{
            transactionService.callback(stkPushStatusResponse);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @GetMapping("/get/all")
    public ResponseEntity<?> fetchAll() {
        try {
            ApiResponse response = transactionService.getAllTransactions();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    } @GetMapping("/get/filteredTransactions")
    public ResponseEntity<?> getFiltredTransactions(
            @RequestParam(name = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
            @RequestParam(name = "toDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate,
            @RequestParam(name = "churchId", required = false) Long churchId,
            @RequestParam(name = "memberNumber", required = false) String memberNumber,
            @RequestParam(name = "familyId", required = false) Long familyId,
            @RequestParam(name = "communityId", required = false) Long communityId,
            @RequestParam(name = "groupsId", required = false) Long groupsId
    ) {
        try {
            ApiResponse response = transactionService.getAllFilteredTransactions(
                    fromDate,
                    toDate,
                    churchId,
                    memberNumber,
                    familyId,
                    communityId,
                    groupsId
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Caught Error: " + e);
            return null;
        }
    }

    @GetMapping("/get/successfullyTransactions")
    public ResponseEntity<?> getAll() {
        try {
            ApiResponse response = transactionService.getMemberTransactions();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }


     @GetMapping("/get/by/givingId/{givingId}")
    public ResponseEntity<?> fetchTransactionByGivingId(@PathVariable("givingId") String givingId) {
        try {
            ApiResponse response = transactionService.getByGivingId(Long.valueOf(givingId));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    @GetMapping("/get/by/memberNumber/{memberNumber}")
    public ResponseEntity<?> fetchTransactionByMemberNumber(@PathVariable("memberNumber") String memberNumber) {
        try {
            ApiResponse response = transactionService.getByMemberNumber(memberNumber);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    @PostMapping("/fetch/daterange")
    public ResponseEntity<?> fetchDateRange(fetchDTO fetchDTO) {
        ApiResponse response = new ApiResponse();
        try {
            System.out.println("Date..................."+datesCalculator.dateFormat(fetchDTO.getStartDate()));
            List<SuccessfullyTransactions> response1 = transactionRepo.fetchByDateRange(datesCalculator.dateFormat(fetchDTO.getStartDate()), datesCalculator.dateFormat(fetchDTO.getEndDate()), fetchDTO.getMemberNumber(),fetchDTO.getCommunityID(),fetchDTO.getFamilyID(),fetchDTO.getChurchID());
          response.setMessage("Found");
          response.setEntity(response1);

          response.setStatusCode(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    @PostMapping("/fetch/daterange/{memberNumber}")
    public ResponseEntity<?> fetchMemberDateRange(@PathVariable String memberNumber , @RequestBody fetchDTO fetchDTO) {
        try {
            ApiResponse response = new ApiResponse();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    @PostMapping("/fetch/daterange/{outStationId}")
    public ResponseEntity<?> fetcheoutStationDateRange(@PathVariable String outStationId , @RequestBody fetchDTO fetchDTO) {
        try {
            ApiResponse response = new ApiResponse();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    @PostMapping("/fetch/daterange/{communityId}")
    public ResponseEntity<?> fetcheCommunityDateRange(@PathVariable String communityId , @RequestBody fetchDTO fetchDTO) {
        try {
            ApiResponse response = new ApiResponse();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @PostMapping("/fetch/daterange/{givingId}")
    public ResponseEntity<?> fetcheGivingDateRange(@PathVariable String givingId , @RequestBody fetchDTO fetchDTO) {
        try {
            ApiResponse response = new ApiResponse();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    @PostMapping("/fetch/daterange/{groupId}")
    public ResponseEntity<?> fetcheGroupDateRange(@PathVariable String groupId , @RequestBody fetchDTO fetchDTO) {
        try {
            ApiResponse response = new ApiResponse();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }











}
