package co.ke.emtechhouse.es.NotificationComponent.TokenComponent;

import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Auth.Members.MembersRepository;

import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@Slf4j
public class TokenService{
    private final TokenRepo tokenRepository;
    private  final MembersRepository membersRepository;

    @Autowired
    public TokenService(TokenRepo tokenRepository, MembersRepository membersRepository){
        this.tokenRepository=tokenRepository;
        this.membersRepository = membersRepository;
    }

    public ApiResponse addToken(Token token){
        try {
            ApiResponse apiResponse = new ApiResponse();
            Optional<Members> checkMember = membersRepository.findByMemberNumber(token.getMemberNumber());
            if (checkMember.isPresent()) {
                Optional<Token> tokenOptional = tokenRepository.findByMemberNumber(token.getMemberNumber());
                if (token.getMemberNumber() == null) {
                    throw new IllegalStateException("User Token is required");
                }

                if (tokenOptional.isPresent()) {
                    //update
                    Token tokenObj = tokenRepository.findByMemberNumber(token.getMemberNumber()).orElseThrow(() -> new IllegalStateException("Device token does not exist"));
                    tokenObj.setDeviceToken(token.getDeviceToken());
                    tokenObj.setLocation(token.getLocation());
                    tokenObj.setAllPermissionGranted(token.isAllPermissionGranted());
                    tokenObj.setPersonalPermissionGranted(token.isPersonalPermissionGranted());
                    tokenObj.setGroupPermissionGranted(token.isGroupPermissionGranted());
                    tokenObj.setFamilyPermissionGranted(token.isFamilyPermissionGranted());
                    tokenObj.setServicePermissionGranted(token.isServicePermissionGranted());
                    Token addedToken = tokenRepository.save(tokenObj);
                    apiResponse.setMessage(HttpStatus.CREATED.getReasonPhrase());
                    apiResponse.setStatusCode(HttpStatus.CREATED.value());
                    apiResponse.setEntity(addedToken);
                } else {
                    //create
                    token.setDeviceToken(token.getDeviceToken());
                    token.setLocation(token.getLocation());
                    token.setMemberNumber(token.getMemberNumber());
                    token.setDeviceToken(token.getDeviceToken());
                    token.setLocation(token.getLocation());
                    token.setAllPermissionGranted(token.isAllPermissionGranted());
                    token.setPersonalPermissionGranted(token.isPersonalPermissionGranted());
                    token.setGroupPermissionGranted(token.isGroupPermissionGranted());
                    token.setFamilyPermissionGranted(token.isFamilyPermissionGranted());
                    token.setServicePermissionGranted(token.isServicePermissionGranted());
                    Token addedToken = tokenRepository.save(token);
                    apiResponse.setMessage(HttpStatus.CREATED.getReasonPhrase());
                    apiResponse.setStatusCode(HttpStatus.CREATED.value());
                    apiResponse.setEntity(addedToken);
                }
                return apiResponse;
            } else {
                apiResponse.setMessage("User does not exist");
                apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
                return apiResponse;
            }

        } catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }
    public ApiResponse<Token> getTokens(){
        try {
            ApiResponse apiResponse = new ApiResponse();
            List<Token> tokenList = tokenRepository.findAll();
            if (tokenList.size() > 0){
                apiResponse.setMessage(HttpStatus.FOUND.getReasonPhrase());
                apiResponse.setStatusCode(HttpStatus.FOUND.value());
                apiResponse.setEntity(tokenList);
            } else {
                apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
                apiResponse.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
            }
            return apiResponse;
        } catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }

    public ApiResponse<Token> getTokenByID(Long id) {
        try {
            ApiResponse apiResponse = new ApiResponse();
            Optional<Token> tokenOptional = tokenRepository.findById(id);
            if (tokenOptional.isPresent()) {
                Token token = tokenOptional.get();
                apiResponse.setMessage(HttpStatus.FOUND.getReasonPhrase());
                apiResponse.setStatusCode(HttpStatus.FOUND.value());
                apiResponse.setEntity(token);
            } else {
                apiResponse.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            }
            return apiResponse;
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }

    public ApiResponse<Token> getTokenByMemberNumber(String memberNumber){
        try {
            ApiResponse apiResponse = new ApiResponse<>();
        Optional<Token> tokenOptional= tokenRepository.findByMemberNumber(memberNumber);
        if (tokenOptional.isPresent()){
            Token token = tokenOptional.get();
            apiResponse.setMessage(HttpStatus.FOUND.getReasonPhrase());
            apiResponse.setStatusCode(HttpStatus.FOUND.value());
            apiResponse.setEntity(token);
        } else {
            apiResponse.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
            apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        }
        return apiResponse;
        } catch (Exception e) {
            log.info("Error {} " + e);
            return null;
        }
    }



    public void deleteToken(long id){
        boolean isPresent= tokenRepository.existsById(id);

        if(!isPresent){
            throw new IllegalStateException("Device Token with id "+id+" does not exist.");
        }
        tokenRepository.deleteById(id);
    }

    @Transactional
    public Token updateToken(String userFk, Token token){
        Token tokenFromDb= tokenRepository.findByMemberNumber(userFk).orElseThrow(()-> new IllegalStateException("Token with id "+userFk+" does not exist."));


        if(token.getDeviceToken()!=null && token.getDeviceToken().length()>0 && !Objects.equals(tokenFromDb.getDeviceToken(),token.getDeviceToken())) {
            tokenFromDb.setDeviceToken(token.getDeviceToken());
        }
        if(token.getLocation()!=null && token.getLocation().length()>0 && !Objects.equals(tokenFromDb.getLocation(), token.getLocation())){
            tokenFromDb.setLocation(token.getLocation());
        }
        if (token.isAllPermissionGranted() != tokenFromDb.isAllPermissionGranted()) {
            tokenFromDb.setAllPermissionGranted(token.isAllPermissionGranted());
        }
        if (token.isPersonalPermissionGranted() != tokenFromDb.isPersonalPermissionGranted()) {
            tokenFromDb.setPersonalPermissionGranted(token.isPersonalPermissionGranted());
        }
        if (token.isFamilyPermissionGranted() != tokenFromDb.isFamilyPermissionGranted()) {
            tokenFromDb.setFamilyPermissionGranted(token.isFamilyPermissionGranted());
        }
        if (token.isGroupPermissionGranted() != tokenFromDb.isGroupPermissionGranted()) {
            tokenFromDb.setGroupPermissionGranted(token.isGroupPermissionGranted());
        }
        if (token.isServicePermissionGranted() != tokenFromDb.isServicePermissionGranted()) {
            tokenFromDb.setServicePermissionGranted(token.isServicePermissionGranted());
        }

        return tokenFromDb;

    }

}


