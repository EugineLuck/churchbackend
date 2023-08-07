package co.ke.emtechhouse.es.BankComponent;

import co.ke.emtechhouse.es.Auth.utils.CONSTANTS;
import co.ke.emtechhouse.es.Auth.utils.HttpInterceptor.UserRequestContext;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BankService {
    @Autowired
    BankRepository bankRepository;

    public ApiResponse<Bank> addBank(Bank bank) {
        try {
            ApiResponse response = new ApiResponse();
            if (UserRequestContext.getCurrentUser().isEmpty()) {
                response.setMessage("User Name not present in the Request Header");
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                return response;
            } else {
                if (bankRepository.existsByBankName(bank.getBankName())) {
                    response.setMessage("Two Banks can not share two names");
                    response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                } else if (bankRepository.existsByBackgroundImage(bank.getBackgroundImage())) {
                    response.setMessage("A Bank can not have two background images");
                    response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                } else {
                    bank.setPostedFlag(CONSTANTS.YES);
                    bank.setPostedTime(new Date());
                    bank.setPostedBy(UserRequestContext.getCurrentUser());
                    bank.setDeletedFlag(CONSTANTS.NO);
                    bank.setVerifiedFlag(CONSTANTS.NO);
                    Bank savedBank = bankRepository.save(bank);
                    response.setMessage(HttpStatus.CREATED.getReasonPhrase());
                    response.setMessage("BANK NAME " + bank.getBankName() + " CREATED SUCCESSFULLY AT " + bank.getPostedTime());
                    response.setStatusCode(HttpStatus.CREATED.value());
                    response.setEntity(savedBank);
                }
            }
            return response;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    public ApiResponse<?> getAllBanks() {
        try {
            if (UserRequestContext.getCurrentUser().isEmpty()) {
                ApiResponse response = new ApiResponse();
                response.setMessage("User Name not present in the Request Header");
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                return response;
            } else {
                List<Bank> bankList = bankRepository.findByDeletedFlag(CONSTANTS.NO);
                ApiResponse response = new ApiResponse();
                if (bankList.size() > 0) {
                    response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                    response.setStatusCode(HttpStatus.FOUND.value());
                    response.setEntity(bankList);
                } else {
                    response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                    response.setStatusCode(HttpStatus.NOT_FOUND.value());
                }
                return response;
            }
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    public ApiResponse<Bank> getBankById(Long id) {
        try {
            ApiResponse response = new ApiResponse();
            if (UserRequestContext.getCurrentUser().isEmpty()) {
                response.setMessage("User name not present in the Request Header");
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                return response;
            } else {
                Optional<Bank> bank = bankRepository.findByDeletedFlagAndId(CONSTANTS.NO, id);
                if (bank.isPresent()) {
                    Bank bank1 = bank.get();
                    response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                    response.setStatusCode(HttpStatus.FOUND.value());
                    response.setEntity(bank1);
                    return response;
                } else {
                    response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                    response.setStatusCode(HttpStatus.NOT_FOUND.value());
                    return response;
                }
            }
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    public ApiResponse<Bank> updateBank(Bank bank) {
        ApiResponse response = new ApiResponse();
        try {
            if (UserRequestContext.getCurrentUser().isEmpty()) {
                response.setMessage("User Name not present in the Request Header");
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
            } else {
                Optional<Bank> bank1 = bankRepository.findById(bank.getId());
                if (bank1.isPresent()) {
                    Bank existingBank = bank1.get();
                    existingBank.setBackgroundImage(bank.getBackgroundImage());
                    existingBank.setBankName(bank.getBankName());
                    Bank savedBank = bankRepository.save(existingBank);
//                    ApiResponse apiResponse = new ApiResponse<>();x
                    response.setMessage("Bank with Id " + bank.getId() + " updated successfully");
                    response.setStatusCode(HttpStatus.OK.value());
                    response.setEntity(savedBank);
                } else {
                    response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                    response.setStatusCode(HttpStatus.NOT_FOUND.value());
                }
            }
            return response;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }


    public ApiResponse<Bank> tempDeleteBank(Long id) {
        try {
            if (UserRequestContext.getCurrentUser().isEmpty()) {
                ApiResponse response = new ApiResponse();
                response.setMessage("User Name not present in the Request Header");
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                return response;
            } else {
                Optional<Bank> bank = bankRepository.findByDeletedFlagAndId(CONSTANTS.NO, id);
                if (bank.isPresent()) {
                    Bank bank1 = bank.get();
                    bank1.setDeletedBy(UserRequestContext.getCurrentUser());
                    bank1.setDeletedFlag(CONSTANTS.YES);
                    bank1.setDeletedTime(new Date());

                    Bank deletedBank = bankRepository.save(bank1);

                    ApiResponse response = new ApiResponse();
                    response.setMessage("Bank deleted successfully");
                    response.setStatusCode(HttpStatus.OK.value());
                    response.setEntity("");
                    return response;
                } else {
                    ApiResponse response = new ApiResponse();
                    response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                    response.setStatusCode(HttpStatus.NOT_FOUND.value());
                    return response;
                }
            }
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }
}
