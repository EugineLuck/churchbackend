package co.ke.emtechhouse.es.Auth.Members;

import co.ke.emtechhouse.es.AppUser.AppUserRepo;
import co.ke.emtechhouse.es.Auth.Requests.*;
import co.ke.emtechhouse.es.Auth.Responses.JwtResponse;
import co.ke.emtechhouse.es.Auth.Responses.MessageResponse;
import co.ke.emtechhouse.es.Auth.Roles.RoleRepository;
import co.ke.emtechhouse.es.Auth.Security.jwt.JwtUtils;
import co.ke.emtechhouse.es.Auth.Security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)

@RestController
@RequestMapping("/church/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AppUserRepo appUserRepo;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    String username = "";
    String modified_by = "";
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    String modified_on = dtf.format(now);



    //Validate Login Credentials for tender Opening
    public String validateLoginCredentials(String userName,String password,String role)
    {
        String response = "";
        if(appUserRepo.searchByUserName(userName).isEmpty())
        {
            response = "Error: Account not Found!";
        }
        else {
            //Check Account status
            String deletestatus = appUserRepo.getDeleteFlag(userName);
            //Locked status
            String locked = appUserRepo.getAccountLockedStatus(userName);
            //Active status
            String active = appUserRepo.getAccountInactiveStatus(userName);

            if (deletestatus.equalsIgnoreCase("Y")) {
                response = "Error: Account Deleted ! Contact Admin!";
            } else if (locked.equalsIgnoreCase("Y") ) {
                response = "Error: Account Locked! Contact Admin!";
            } else if (active.equalsIgnoreCase("N")) {
                response = "Error: Account Inactive! Contact Admin!";
            }
            else {
                try {
                    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                    List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
                    for (String s : roles) {
                        if (s.contains(role)) {
                            response = s;
                            break;
                        }
                        else
                        {
                            response = "Action Denied for this Role!";
                            break;
                        }
                    }
                }
                catch (Exception e)
                {
                    response = e.getLocalizedMessage();
                }
            }
        }
        return response;
    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        System.out.println(loginRequest.getUserName());
        if(appUserRepo.searchByUserName(loginRequest.getUserName()).isEmpty())
        {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Account not Found!"));
        }
        else {
            //Check Account status
            String deletestatus = appUserRepo.getDeleteFlag(loginRequest.getUserName());
            //Locked status
            String locked = appUserRepo.getAccountLockedStatus(loginRequest.getUserName());
            //Active status
            String active = appUserRepo.getAccountInactiveStatus(loginRequest.getUserName());
            //Log in status
            //boolean isLoggedin = userRepository.getLogInStatus(loginRequest.getUsername());

            if (deletestatus.equalsIgnoreCase("Y")) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Account Deleted ! Contact Admin!"));
            } else if (locked.equalsIgnoreCase("Y") ) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Account Locked! Contact Admin!"));
            } else if (active.equalsIgnoreCase("N")) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Account Inactive! Contact Admin!"));
            }
            //else if(isLoggedin)
            //{
            //  return ResponseEntity.badRequest().body(new MessageResponse("Error: User Already Logged In! Log Out First or Contact Admin!"));
            //}
            else {
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwt = jwtUtils.generateJwtToken(authentication);

                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());

                //Update Logged in status to true

//                userRepository.updateLogInToTrue("Y", modified_on, loginRequest.getMemberNumber(), loginRequest.getMemberNumber());

                //Add records to audit table

                return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),userDetails.getImageBanner(), roles));
            }
        }
    }

//    //Sign Out
//    @PostMapping("/logout")
//    public ResponseEntity<?> signOut(@RequestBody LogOutRequest logOutRequest, Authentication auth, HttpServletRequest request) {
//        if(userRepository.searchByMemberNumber(logOutRequest.getMemberNumber()).isEmpty()){
//            //Update Logged in status to true
//            userRepository.updateLogInToFalse("N", modified_on, auth.getName(), auth.getName());
//
//            //Add Audit
//            return ResponseEntity.ok(new MessageResponse("Logged Out Successfully!"));
//        }
//        else
//        {
//            return ResponseEntity.ok(new MessageResponse("Account not Found!"));
//        }
//    }
}
