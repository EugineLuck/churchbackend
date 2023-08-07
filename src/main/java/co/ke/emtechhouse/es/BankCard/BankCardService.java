package co.ke.emtechhouse.es.BankCard;

import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Auth.Members.MembersRepository;
import co.ke.emtechhouse.es.Auth.utils.CONSTANTS;
import co.ke.emtechhouse.es.Auth.utils.HttpInterceptor.UserRequestContext;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.BankComponent.Bank;
import co.ke.emtechhouse.es.BankComponent.BankRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BankCardService {
    @Autowired
    BankCardRepo bankCardRepo;
    @Autowired
    MembersRepository membersRepository;
    @Autowired
    BankRepository bankRepository;

    public ApiResponse<BankCard> addBankCard(BankCard bankCard) {
        try {
            ApiResponse response = new ApiResponse();
            if (UserRequestContext.getCurrentUser().isEmpty()) {
                response.setMessage("User Name not present in the Request Header");
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
            } else {
                Optional<Members> checkUser = membersRepository.findById(bankCard.getUsersFk());
                if (checkUser.isPresent()){
                    Optional<Bank> checkBank = bankRepository.findById(bankCard.getBankFk());
                    if (checkBank.isPresent()) {
                        if (bankCardRepo.existsByUsersFkAndBankFkAndCardNumberAndCvv(bankCard.getUsersFk(), bankCard.getBankFk(), bankCard.getCardNumber(), bankCard.getCvv())) {
                            response.setMessage("Bank card already added");
                            response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                        } else {
                            bankCard.setPostedFlag(CONSTANTS.YES);
                            bankCard.setPostedTime(new Date());
                            bankCard.setPostedBy(UserRequestContext.getCurrentUser());
                            bankCard.setDeletedFlag(CONSTANTS.NO);
                            bankCard.setVerifiedFlag(CONSTANTS.NO);
                            BankCard savedBankCard = bankCardRepo.save(bankCard);
                            response.setMessage(HttpStatus.CREATED.getReasonPhrase());
                            response.setMessage("BANK NAME " + bankCard.getCardHolderName() + " CREATED SUCCESSFULLY AT " + bankCard.getPostedTime());
                            response.setStatusCode(HttpStatus.CREATED.value());
                            response.setEntity(savedBankCard);
                        }
                    } else {
                        response.setMessage("Bank not found");
                        response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                    }
                }else {
                    response.setMessage("User not found");
                    response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                }

            }
            return response;
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    public ApiResponse<?> getAllBankCards() {
        try {
            if (UserRequestContext.getCurrentUser().isEmpty()) {
                ApiResponse response = new ApiResponse();
                response.setMessage("User Name not present in the Request Header");
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                return response;
            } else {
                List<BankCard> bankCardList = bankCardRepo.findByDeletedFlag(CONSTANTS.NO);
                ApiResponse response = new ApiResponse();
                if (bankCardList.size() > 0) {
                    response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                    response.setStatusCode(HttpStatus.FOUND.value());
                    response.setEntity(bankCardList);
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

    public ApiResponse<BankCard> getBankCardsByUsersId(Long usersId) {
        try {
            ApiResponse response = new ApiResponse();
            if (UserRequestContext.getCurrentUser().isEmpty()) {
                response.setMessage("User name not present in the Request Header");
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                return response;
            } else {
                Optional<Members> user = membersRepository.findById(usersId);
                if (user.isPresent()) {
                    List<BankCard> bankCards = bankCardRepo.findByUsersFk(usersId);
                    if (bankCards.size() > 0) {
                        response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                        response.setStatusCode(HttpStatus.FOUND.value());
                        response.setEntity(bankCards);
                        return response;
                    } else {
                        response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                        response.setStatusCode(HttpStatus.NOT_FOUND.value());
                        return response;
                    }
                } else {
                    response.setMessage("User not found");
                    response.setStatusCode(HttpStatus.NOT_FOUND.value());
                    return response;
                }
            }
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    public ApiResponse<BankCard> getBankCardById(Long id) {
        try {
            ApiResponse response = new ApiResponse();
            if (UserRequestContext.getCurrentUser().isEmpty()) {
                response.setMessage("User name not present in the Request Header");
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                return response;
            } else {
                Optional<BankCard> bankCard = bankCardRepo.findByDeletedFlagAndId(CONSTANTS.NO, id);
                if (bankCard.isPresent()) {
                    BankCard bankCard1 = bankCard.get();
                    response.setMessage(HttpStatus.FOUND.getReasonPhrase());
                    response.setStatusCode(HttpStatus.FOUND.value());
                    response.setEntity(bankCard1);
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

    public ApiResponse<BankCard> updateBankCard(BankCard bankCard) {
        try {
            if (UserRequestContext.getCurrentUser().isEmpty()) {
                ApiResponse response = new ApiResponse();
                response.setMessage("User Name not present in the Request Header");
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                return response;
            } else {
                Optional<BankCard> bankCard1 = bankCardRepo.findById(bankCard.getId());
                if (bankCard1.isPresent()) {
                    BankCard existingBankCard = bankCard1.get();
                    bankCard.setPostedFlag(existingBankCard.getPostedFlag());
                    bankCard.setPostedTime(existingBankCard.getPostedTime());
                    bankCard.setPostedBy(existingBankCard.getPostedBy());
                    bankCard.setDeletedFlag(existingBankCard.getDeletedFlag());
                    bankCard.setModifiedBy(UserRequestContext.getCurrentUser());
                    bankCard.setModifiedTime(new Date());
                    bankCard.setVerifiedFlag(existingBankCard.getVerifiedFlag());
                    BankCard savedBankCard = bankCardRepo.save(bankCard);
                    ApiResponse apiResponse = new ApiResponse<>();
                    apiResponse.setMessage("Bank Card with Id " + bankCard.getId() + " updated successfully");
                    apiResponse.setStatusCode(HttpStatus.OK.value());
                    apiResponse.setEntity(savedBankCard);
                    return apiResponse;
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

    public ApiResponse<BankCard> tempDeleteBankCard(Long id) {
        try {
            if (UserRequestContext.getCurrentUser().isEmpty()) {
                ApiResponse response = new ApiResponse();
                response.setMessage("User Name not present in the Request Header");
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                return response;
            } else {
                Optional<BankCard> bankCard = bankCardRepo.findByDeletedFlagAndId(CONSTANTS.NO, id);
                if (bankCard.isPresent()) {
                    BankCard bankCard1 = bankCard.get();
                    bankCard1.setDeletedBy(UserRequestContext.getCurrentUser());
                    bankCard1.setDeletedFlag(CONSTANTS.YES);
                    bankCard1.setDeletedTime(new Date());

                    BankCard deletedBankCard = bankCardRepo.save(bankCard1);

                    ApiResponse response = new ApiResponse();
                    response.setMessage("Bank Card deleted successfully");
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

