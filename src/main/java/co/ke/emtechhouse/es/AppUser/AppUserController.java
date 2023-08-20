package co.ke.emtechhouse.es.AppUser;


import co.ke.emtechhouse.es.Advertisement.Advertisement;
import co.ke.emtechhouse.es.Auth.DTO.Mailparams;
import co.ke.emtechhouse.es.Auth.Requests.ChangePassword;
import co.ke.emtechhouse.es.Auth.Requests.Forgotpassword;
import co.ke.emtechhouse.es.Auth.Requests.LoginRequest;
import co.ke.emtechhouse.es.Auth.Responses.JwtResponse;
import co.ke.emtechhouse.es.Auth.Roles.ERole;
import co.ke.emtechhouse.es.Auth.Roles.Role;
import co.ke.emtechhouse.es.Auth.Roles.RoleRepository;
import co.ke.emtechhouse.es.Auth.Security.jwt.JwtUtils;
import co.ke.emtechhouse.es.Auth.utils.PasswordGeneratorUtil;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.Auth.utils.Session.Activesession;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/AppUser")
public class AppUserController {
    @Autowired
    AppUserRepo appUserRepo;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    AppUserService appUserService;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> registerAppUser(@Valid @RequestBody UserReg userReg) throws MessagingException {
        ApiResponse response = new ApiResponse();

        if (appUserRepo.existsByUserName(userReg.getUserName())) {
            response.setMessage("Username is already taken!");
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setEntity("");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        AppUser appUser = new AppUser();
        Set<Role> roles = new HashSet<>();
        if (userReg.getRoleFk() == null) {
            Role appUserRole = roleRepository.findByName(ERole.ROLE_APP_USER.toString())
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(appUserRole);
        }
        else {
            Role userRole = roleRepository.findById(userReg.getRoleFk())
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }
        appUser.setRoles(roles);

        appUser.setUserName(userReg.getUserName());
        appUser.setPassword(encoder.encode(userReg.getPassword()));

        appUserService.appUserRegistration(appUser);

        response.setMessage("You have successfully registered as a System User Login to signUp for membership");
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setEntity(appUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }


    @PostMapping("/signin")
    public ApiResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws MessagingException {
        ApiResponse response = new ApiResponse();
        AppUser appUser = (AppUser) appUserRepo.findByUserName(loginRequest.getUserName()).orElse(null);
        System.out.println("check2");
        if (appUser == null) {
            response.setMessage("Account Access Restricted! Check with the  System Admin");
            response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        } else {
//        Check if Account is Locked
            System.out.println("check3");
            if (appUser.isAcctLocked()) {
                response.setMessage("Account is Locked!");
                response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            } else {
                System.out.println("check4");
                if (appUser.getDeletedFlag() == 'Y') {
                    response.setMessage("This account has been deleted!");
                    response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
                } else {
                    if (appUser.isActive()) {
                        System.out.println("check5");
//                        System.out.println("loginRequest.getPassword() "+loginRequest.getPassword());
//                        System.out.println("loginRequest.getUserName() "+loginRequest.getUserName());

                        Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        String jwt = jwtUtils.generateJwtToken(authentication);
                        //String jwt = "token";
                        System.out.println(jwt);
                        appUser.setAccessToken(jwt );


                        String username = loginRequest.getUserName();
                        System.out.println("--------------------------------------------------------------");
                        response.setMessage("Member successfully logged in");
                        response.setStatusCode(HttpStatus.OK.value());
                        response.setEntity(appUser);
                    } else {
                        response.setMessage("Member has not been verified");
                        response.setEntity(HttpStatus.NOT_ACCEPTABLE.value());
                    }
                }
            }
        }
        return response;
    }
    private JwtResponse getAccessToken(String userName) {
        Optional<Object> appUserCheck = appUserRepo.findByUserName(userName);
        AppUser appUser = (AppUser) appUserCheck.get();
        String jwt = jwtUtils.generateJwtTokenWithUserName(userName);
        Collection<Object> roles = Collections.singleton(appUser.getRoles());

//        String otp = "Your otp code is " + otpService.generateOTP(user.getUsername());
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(jwt);
        jwtResponse.setId(appUser.getId().longValue());
        jwtResponse.setUsername(userName);

        jwtResponse.setFirstLogin(appUser.getFirstLogin());
//        jwtResponse.setRoles(roles);
//        response.setEntity(jwtResponse);
        appUser.setActive(true);
        appUserRepo.save(appUser);
        return jwtResponse;
//        response.setMessage("Authenticated Successfully! Kindly verify token to complete Authorization process.");
//        response.setStatusCode(HttpStatus.OK.value());
    }
    @GetMapping(path = "/active/sessions")
    public ResponseEntity<?> getActiveSession() throws JsonProcessingException {
        ApiResponse response = new ApiResponse();
        List<Activesession> responseArray = new ArrayList<>();
////        HashMap<String, String> map = (HashMap<String, String>) sessionManager.getActiveSession();
//        Gson g = new Gson();
//        for (Map.Entry<String, String> set :
//                map.entrySet()) {
//            JwtResponse jwtResponse  = g.fromJson(set.getValue(),JwtResponse.class);
//            Activesession activesession = new Activesession();
//            activesession.setUuid(jwtResponse.getUuid());
//            activesession.setUsername(jwtResponse.getUsername());
//            activesession.setStatus(jwtResponse.getStatus());
//            activesession.setLoginAt(jwtResponse.getLoginAt());
//            activesession.setAddress(jwtResponse.getAddress());
//            activesession.setOs(jwtResponse.getOs());
//            activesession.setBrowser(jwtResponse.getBrowser());
//            responseArray.add(activesession);
//        }
        response.setMessage(HttpStatus.OK.getReasonPhrase());
        response.setStatusCode(HttpStatus.OK.value());
        response.setEntity(responseArray);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/get/all")
    public ResponseEntity<Object> getAllAppUsers() {
        try {
            List<AppUser> users = appUserService.getAll();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error" + e);
            return null;
        }
    }


    @PostMapping("/password/reset")
    public ResponseEntity<?> forgotPassword(@RequestBody Forgotpassword forgotpassword) throws MessagingException {
        ApiResponse response = new ApiResponse();
        if (!appUserRepo.existsByUserName(forgotpassword.getUserName())) {
            response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setEntity("");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            PasswordGeneratorUtil passwordGeneratorUtil = new PasswordGeneratorUtil();
            String newPass = encoder.encode(forgotpassword.getNewPassword());

            Optional<AppUser> user = appUserRepo.getByuserName(forgotpassword.getUserName());
            if (user.isPresent()) {
                AppUser user1 = user.get();
                user1.setPassword(newPass);
                appUserRepo.save(user1);
                String message = "Dear " + user1.getUserName() + " your password has been updated successfully!";
                response.setMessage(message);
                response.setStatusCode(HttpStatus.OK.value());
                response.setEntity("");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setMessage("User with Username not found!");
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setEntity("");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
    }



    @PostMapping("/password/change")
    public ResponseEntity<?> chagePassword(@RequestBody ChangePassword changepassword) throws MessagingException {
        ApiResponse response = new ApiResponse();
        if (!appUserRepo.existsByUserName(changepassword.getUserName())) {
            response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setEntity("");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            PasswordGeneratorUtil passwordGeneratorUtil = new PasswordGeneratorUtil();
            String newPass = encoder.encode(changepassword.getNewPassword());

            Optional<AppUser> user = appUserRepo.getByuserName(changepassword.getUserName());
            if (user.isPresent()) {
                AppUser user1 = user.get();

                // Check if the new password is the same as the old password
                if (encoder.matches(changepassword.getNewPassword(), user1.getPassword())) {
                    response.setMessage("Cannot use the same password");
                    response.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    response.setEntity("");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
                user1.setPassword(newPass);
                appUserRepo.save(user1);
                String message = "Dear " + user1.getUserName() + " your password has been Changed successfully!";
                response.setMessage(message);
                response.setStatusCode(HttpStatus.OK.value());
                response.setEntity("");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setMessage("User with Username not found!");
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setEntity("");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
    }


}
