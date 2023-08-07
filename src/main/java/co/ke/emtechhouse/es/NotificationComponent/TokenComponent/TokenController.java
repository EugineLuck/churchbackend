package co.ke.emtechhouse.es.NotificationComponent.TokenComponent;


import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/deviceTokens")
public class TokenController {
    private final TokenService tokenService;

    @Autowired
    public TokenController(TokenService tokenService){
        this.tokenService=tokenService;
    }
    @PostMapping("/add")
    public ResponseEntity<Object> addDeviceToken(@RequestBody Token token){
        ApiResponse tokenObj = tokenService.addToken(token);
            return new ResponseEntity<>(tokenObj, HttpStatus.OK);
    }
    @GetMapping("/get/all/device/tokens")
    public ResponseEntity<?> getAllDeviceTokens(){
        try {
            ApiResponse response = tokenService.getTokens();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            log.info("Error" + e);
            return null;
        }
    }

    @GetMapping("/get/token/by/id/{tokenId}")
    public ResponseEntity<Object>  getTokenById(@PathVariable("tokenId") Long id){
        ApiResponse apiResponse = tokenService.getTokenByID(id);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get/token/by/memberNumber/{memberNumber}")
    public ResponseEntity<Object> getTokenByMemberNumber(@PathVariable("memberNumber") String memberNumber){
        try {
            ApiResponse apiResponse = tokenService.getTokenByMemberNumber(memberNumber);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);

        }catch (Exception e){
            log.info("Error" + e);
            return  null;
        }
    }
    @PutMapping("/update/token/{memberNumber}")
    public ResponseEntity<Object> updateDeviceToken(@RequestBody Token token,@PathVariable("memberNumber") String memberNumber){
        Token tokenObj= tokenService.updateToken(memberNumber,token);


        if (tokenObj != null) {
            return ResponseEntity.status(HttpStatus.OK).body(tokenObj);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/delete/token/{tokenId}")
    public ResponseEntity<Object> deleteDeviceToken(@PathVariable("tokenId") Long id){
         tokenService.deleteToken(id);

        Map<String, String> message= new HashMap<>();
        message.put("message","Device token was successfully deleted");
        return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
    }

}
