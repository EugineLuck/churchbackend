package co.ke.emtechhouse.es.Auth.Members;
import co.ke.emtechhouse.es.Auth.utils.DatesCalculator;
import co.ke.emtechhouse.es.IDNO.IDNOCheckerRepository;
import co.ke.emtechhouse.es.IDNO.IDNOController;
import co.ke.emtechhouse.es.IDNO.IDNODetails;
import co.ke.emtechhouse.es.IDNO.IDNOdto;
import co.ke.emtechhouse.es.NotificationComponent.TokenComponent.Token;
import co.ke.emtechhouse.es.NotificationComponent.TokenComponent.TokenRepo;
import org.apache.commons.text.WordUtils;

import co.ke.emtechhouse.es.Auth.MailService.MailService;

import co.ke.emtechhouse.es.Auth.Requests.*;
import co.ke.emtechhouse.es.Auth.Responses.MessageResponse;
import co.ke.emtechhouse.es.Auth.Roles.ERole;
import co.ke.emtechhouse.es.Auth.Roles.Role;
import co.ke.emtechhouse.es.Auth.Roles.RoleRepository;
import co.ke.emtechhouse.es.Auth.Security.jwt.JwtUtils;
import co.ke.emtechhouse.es.Auth.utils.HttpInterceptor.UserRequestContext;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import co.ke.emtechhouse.es.Auth.utils.Session.Activesession;
import co.ke.emtechhouse.es.Community.CommunityService;
import co.ke.emtechhouse.es.Family.Family;
import co.ke.emtechhouse.es.Family.FamilyRepository;
import co.ke.emtechhouse.es.Groups.GroupMemberComponent.GroupMember;
import co.ke.emtechhouse.es.Groups.GroupMemberComponent.GroupMemberRepo;
import co.ke.emtechhouse.es.Groups.Groups;
import co.ke.emtechhouse.es.Groups.GroupsRepo;
import co.ke.emtechhouse.es.OutStation.OutStationRepository;
import co.ke.emtechhouse.es.OutStation.OutStationService;
import co.ke.emtechhouse.es.SmsComponent.Emtech.Dtos.Dtos.SmsDto;
import co.ke.emtechhouse.es.SmsComponent.Emtech.Dtos.EmtSmsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@RestController

@RequestMapping("/api/v1/auth")
@Slf4j
public class MembersController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    MembersRepository membersRepository;
    @Autowired
    OutStationRepository outStationRepository;
    @Autowired
    FamilyRepository familyRepo;

    @Autowired
    IDNOController idnoController;
    @Autowired
    private FamilyRepository frepo;
    @Autowired
    private DatesCalculator datesCalculator;
 @Autowired
    private GroupMemberRepo groupMemberRepo;

    @Autowired
    RoleRepository roleRepository;
 @Autowired
 GroupsRepo groupsRepo;
 @Autowired
 TokenRepo tokenRepo;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    IDNOCheckerRepository idnoCheckerRepository;


    @Autowired
    MembersService membersService;
    @Autowired
    MailService mailService;

    @Autowired
    CommunityService communityService;
    @Autowired
    OutStationService outStationService;
    @Autowired
    private EmtSmsService emtSmsService;
    @Value("${organisation.maxNoOfTellers}")
    private Integer maxNoOfTellers;
    @Value("${spring.application.useOTP}")
    private String useOTP;
    @Value("${spring.application.otpProd}")
    private String otpProd;
    @Value("${spring.application.otpTestMail}")
    private String otpTestMail;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    String isloggedin = "N";
    String status = "Inactive";



    @PostMapping("/signup")
    public ResponseEntity<?> registerMember(@Valid @RequestBody SignupRequest signUpRequest) throws MessagingException, ParseException {
        ApiResponse response = new ApiResponse();

        try{

            Optional<Members> existing = membersRepository.findByPhoneNumber(signUpRequest.getPhoneNo());
            if (existing.isPresent()) {
                response.setMessage("The Phone number is already registered to another account!");
                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
                response.setEntity("");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }



//        IDNOdto details = new IDNOdto();
////        details.setDateOfBirth(signUpRequest.getDateOfBirth());
//        details.setLastName(signUpRequest.getLastName());
//        details.setDateOfBirth("1990-01-01");
//        details.setFirstName(signUpRequest.getFirstName());
//        details.setDocumentNumber(signUpRequest.getNationalID());
//
//        ApiResponse verify = idnoController.verifyNow(details);
//
//        if(verify.getStatusCode() == 302){

            String familyNumber = generateFamily();
            String memberNumber = generateMemberNumber();

            // Capitalize the first letter of each word in the first name and last name
            String capitalizedFirstName = WordUtils.capitalizeFully(signUpRequest.getFirstName());
            String capitalizedLastName = WordUtils.capitalizeFully(signUpRequest.getLastName());
            // Save Family if familyId is not provided
            Long familyId = signUpRequest.getFamilyId();
            if (familyId == null) {
                Family family = new Family();
                family.setFamilyNumber(familyNumber);
                family.setPostedTime(new Date());
                family.setFamilyName(signUpRequest.getFamilyName());
                family.setOutStationId(signUpRequest.getOutStationId());
                family.setCommunityId(signUpRequest.getCommunityId());
                frepo.save(family);
                familyId = family.getId();
                familyNumber = family.getFamilyNumber();
            }

            Members members = new Members();
            Token token = new Token();
            members.setUsername(signUpRequest.getPhoneNo());
            members.setEmail(signUpRequest.getEmail());
            members.setPostedTime(dtf.format(now));
            members.setFirstName(capitalizedFirstName);
            members.setLastName(capitalizedLastName);
            members.setModeOfRegistration(signUpRequest.getModeOfRegistration());
            members.setPhoneNumber(convertPhoneNumber(signUpRequest.getPhoneNo()));
            members.setEmail(signUpRequest.getEmail());
            members.setMemberRole(signUpRequest.getRole());
            members.setNationalID(signUpRequest.getNationalID());
            members.setAppId(signUpRequest.getAppId());
            members.setIdOwnership(signUpRequest.getIdOwnership());
            members.setCommunityId(signUpRequest.getCommunityId());
            members.setGender(signUpRequest.getGender());
            members.setFamilyId(familyId);
            members.setOutStationId(signUpRequest.getOutStationId());
            token.setDeviceToken(signUpRequest.getDeviceToken());
            token.setMemberNumber(memberNumber);
            members.setMemberNumber(memberNumber);
            members.setDeletedFlag("N");

            members.setDisabilityType(signUpRequest.getDisabilityType());
            members.setDisability(signUpRequest.getDisability());
            members.setCurrentLocation(signUpRequest.getCurrentLocation());
            members.setProfession(signUpRequest.getProfession());
            members.setStayPeriod(signUpRequest.getStayPeriod());


//            members.setD("N");
            members.setDateOfBirth(signUpRequest.getDateOfBirth());

            // Set roles
            Set<Role> roles = new HashSet<>();
            if (signUpRequest.getRoleFk() == null) {
                Role memberRole = roleRepository.findByName(ERole.ROLE_MEMBER.toString())
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(memberRole);
            } else {
                Role memberRole = roleRepository.findById(signUpRequest.getRoleFk())
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(memberRole);
            }
            members.setRoles(roles);



            // Save the member without adding groups to the GroupMember table yet

            Members savedMembers = membersRepository.save(members);
            Token saveToken = tokenRepo.save(token);

            List<Long> groupsId = signUpRequest.getGroupsId();
            if (groupsId != null && !groupsId.isEmpty()) {
                List<GroupMember> groupMembers = new ArrayList<>();
                for (Long groupId : groupsId) {
                    Groups group = groupsRepo.findById(groupId)
                            .orElseThrow(() -> new RuntimeException("Error: Group with id " + groupId + " not found."));

                    GroupMember groupMember = new GroupMember();
                    groupMember.setGroup(group);
                    groupMember.setMember(savedMembers);
                    groupMember.setStatus("Active");
                    groupMember = groupMemberRepo.save(groupMember);
                    groupMembers.add(groupMember);
                }

            }





            String message = "CONGRATULATIONS " + members.getFirstName() + "!" +
                    " You have successfully registered to EMT Church. " +
                    "Your member number is " + memberNumber + ". And your Family Number is " + familyNumber + " Use your username to login.";
            emtSmsService.sendSms(new SmsDto(members.getPhoneNumber(), message));

            response.setMessage("CONGRATULATIONS " + members.getFirstName() + "!" +
                    " You have successfully registered to EMT Church. " +
                    "Your member number is " + memberNumber + ". And your Family Number is " + familyNumber + " Use your username to login.");
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setEntity(savedMembers);


//        }else{
//            response.setMessage(verify.getMessage());
//            response.setStatusCode(verify.getStatusCode());
//        }


            // Send SMS and create the response

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        }catch (Exception e){
            System.out.println("Error "+ e);
            return  null;
        }


    }



//        @PostMapping("/update/{memberNumber}")


    @PostMapping("/addFamilyMember")
    public ResponseEntity<?> addFamilyMember(@Valid @RequestBody SignupRequest signUpRequest) throws MessagingException {
        ApiResponse response = new ApiResponse();

        Optional<Family> existing = familyRepo.getFamilyByNId(signUpRequest.getNationalID());

        if (existing.isPresent()) {
            Family fname = existing.get();

                String familyName = fname.getFamilyName();
                String fnumber = fname.getFamilyNumber();
                String memberNumber = generateMemberNumber();


                Members members = new Members();
                members.setUsername(signUpRequest.getPhoneNo());
                members.setEmail(signUpRequest.getEmail());
                members.setFamilyId(signUpRequest.getFamilyId());
                members.setPostedTime(dtf.format(now));
                members.setFirstName(signUpRequest.getFirstName());
                members.setLastName(signUpRequest.getLastName());
                members.setModeOfRegistration(signUpRequest.getModeOfRegistration());
                members.setPhoneNumber(signUpRequest.getPhoneNo());
                members.setEmail(signUpRequest.getEmail());
                members.setMemberRole(signUpRequest.getRole());
                members.setNationalID(signUpRequest.getNationalID());
                members.setIdOwnership(signUpRequest.getIdOwnership());
                members.setCommunityId(signUpRequest.getCommunityId());
                members.setGender(signUpRequest.getGender());

                members.setOutStationId(signUpRequest.getOutStationId());
                members.setMemberNumber(memberNumber);
                members.setDateOfBirth(signUpRequest.getDateOfBirth());

                // Set roles
                Set<Role> roles = new HashSet<>();
                if (signUpRequest.getRoleFk() == null) {
                    Role memberRole = roleRepository.findByName(ERole.ROLE_MEMBER.toString())
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(memberRole);
                } else {
                    Role memberRole = roleRepository.findById(signUpRequest.getRoleFk())
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(memberRole);
                }
                members.setRoles(roles);

                // Save the member without adding groups to the GroupMember table yet
                Members savedMembers = membersRepository.save(members);

                List<Long> groupsId = signUpRequest.getGroupsId();
                if (groupsId != null && !groupsId.isEmpty()) {
                    List<GroupMember> groupMembers = new ArrayList<>();
                    for (Long groupId : groupsId) {
                        Groups group = groupsRepo.findById(groupId)
                                .orElseThrow(() -> new RuntimeException("Error: Group with id " + groupId + " not found."));

                        GroupMember groupMember = new GroupMember();
                        groupMember.setGroup(group);
                        groupMember.setMember(savedMembers);
                        groupMember.setStatus("Active");
                        groupMember = groupMemberRepo.save(groupMember);
                        groupMembers.add(groupMember);
                    }


                // Send SMS and create the response
                String message = "CONGRATULATIONS " + members.getFirstName() + "!" +
                        " You have successfully registered to EMT Church. " +
                        "Your member number is " + memberNumber + ". As part of " + familyName +" "+ fnumber+ " Family.";
                emtSmsService.sendSms(new SmsDto(members.getPhoneNumber(), message));

                response.setMessage("CONGRATULATIONS " + members.getFirstName() + "!" +
                        " You have successfully registered to EMT Church. " +
                        "Your member number is " + memberNumber + ". As part of " + familyName  +" "+ fnumber+ " Family.");
                response.setStatusCode(HttpStatus.CREATED.value());
                response.setEntity(savedMembers);

        }
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }









    @GetMapping(path = "/members/admin")
    public List<Members> findByIsAdminAndDeletedFlag() {
        return membersRepository.findByIsAdminAndDeletedFlag(true, 'N');
    }






    @PutMapping("/updateMember")
    public ResponseEntity<?> updateMember(@Valid @RequestBody UpdateMember updateMember) {
        ApiResponse response = new ApiResponse();

        membersRepository.updateMemberDetails(
                updateMember.getNationalID(),
                updateMember.getIdOwnership(),
                updateMember.getEmail(),
                updateMember.getPhoneNo(),
                updateMember.getCommunityId(),
                updateMember.getFamilyId(),
                updateMember.getMemberRole(),
                updateMember.getMemberNumber()
        );

        Optional<Members> existingMember = membersRepository.findByMemberNumber(updateMember.getMemberNumber());

        if (existingMember.isPresent()) {
            Members member = existingMember.get();
            List<Long> groupsId = updateMember.getGroupsId();

            if (groupsId != null && !groupsId.isEmpty()) {
                // Find existing group memberships of the member
                List<GroupMember> existingGroupMemberships = groupMemberRepo.findByMember(member);

                // Add new group memberships and update existing ones
                for (Long groupId : groupsId) {
                    Groups group = groupsRepo.findById(groupId)
                            .orElseThrow(() -> new RuntimeException("Error: Group with id " + groupId + " not found."));

                    // Check if the member has an existing membership for this group
                    Optional<GroupMember> existingMembership = existingGroupMemberships.stream()
                            .filter(groupMember -> groupMember.getGroup().getId().equals(groupId))
                            .findFirst();

                    if (existingMembership.isPresent()) {
                        GroupMember groupMember = existingMembership.get();
                        groupMember.setStatus("Active");
                        groupMemberRepo.save(groupMember);
                    } else {
                        GroupMember newGroupMember = new GroupMember();
                        newGroupMember.setGroup(group);
                        newGroupMember.setMember(member);
                        newGroupMember.setStatus("Active");
                        groupMemberRepo.save(newGroupMember);
                    }
                }

                // Update the status of existing group memberships that are not in groupsId
                existingGroupMemberships.stream()
                        .filter(groupMember -> !groupsId.contains(groupMember.getGroup().getId()))
                        .forEach(groupMember -> {
                            groupMember.setStatus("Inactive");
                            groupMemberRepo.save(groupMember);
                        });
            }
        }
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Member Updated successfully");
        response.setEntity(updateMember);

        return ResponseEntity.ok(response);
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

    @GetMapping(path = "/find/by/username/{members}")
    public ApiResponse getUserByUsername(@PathVariable String user) {
        ApiResponse response = new ApiResponse<>();
        Optional<Members> members1 = membersRepository.findByUsername(user);
        if (members1.isPresent()) {
            Members members = members1.get();
            response.setMessage(HttpStatus.FOUND.getReasonPhrase());
            response.setStatusCode(HttpStatus.FOUND.value());
            response.setEntity(members);
            return response;
//            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Email not found");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return response;
        }
//        return userRepository.findByUsername(user).orElse(null);
    }





    @PutMapping(path = "/members/update/{memberNumber}")
    public ResponseEntity<?> updateMember(@Valid @RequestBody MemberUpdateDTO signUpRequest, @PathVariable String memberNumber) throws MessagingException {
        ApiResponse response = new ApiResponse();
        Optional<Members> membersOptional = membersRepository.findByMemberNumber(signUpRequest.getMemberNumber());
        if (membersOptional.isPresent()) {
            log.info("User exist...");




            response.setMessage("Member " + signUpRequest.getMemberNumber() + " has been updated successfully!");
            response.setStatusCode(HttpStatus.OK.value());
            response.setEntity("");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setEntity("");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Members> updateUser(@PathVariable Long id, @RequestBody Members user) {
        Optional<Members> optionalMembers = membersRepository.findById(id);
        if (!optionalMembers.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Members existingMember = optionalMembers.get();
        existingMember.setDateOfBirth(user.getDateOfBirth());
        Members updatedMember = membersRepository.save(existingMember);
        return ResponseEntity.ok(updatedMember);
    }
//
//    @GetMapping(path = "/all/members")
//    public List<MemberDetails> getAllMembers() {
//        return membersRepository.getAllMembers();
//    }
    @GetMapping(path = "/roles")
    public ResponseEntity<?> getRoles() {
        return ResponseEntity.ok().body(roleRepository.findAll());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> signOut(@RequestBody LogOutRequest logOutRequest, Authentication auth, HttpServletRequest request) {
        if(membersRepository.searchByMemberNumber(logOutRequest.getMemberNumber()).isEmpty()){
            //Update Logged in status to true
            membersRepository.updateLogInToFalse("N", auth.getName(), auth.getName());

            //Add Audit
            return ResponseEntity.ok(new MessageResponse("Logged Out Successfully!"));
        }
        else
        {
            return ResponseEntity.ok(new MessageResponse("Account not Found!"));
        }
    }

    @PutMapping(path = "/lock/{id}")
    public ApiResponse lock(@PathVariable Long id) {
        ApiResponse response = new ApiResponse();
        Optional<Members> members = membersRepository.findById(id);
        if (members.isPresent()) {
//            check if account is active
            Members members1 = members.get();
            members1.setAcctLocked(true);
            membersRepository.save(members1);
            response.setMessage("Locked Successful!");
            response.setStatusCode(HttpStatus.OK.value());
            return response;
        } else {
            response.setMessage("User Not Found");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return response;
        }
    }
    @PutMapping("/updaterole")
    public ResponseEntity<?> updateMemberRole(@Valid @RequestBody UpdateMemberRole update) {
        ApiResponse response = new ApiResponse();
        Optional<Members> members = membersRepository.findByMemberNumber(update.getMemberId());
        Optional<Role> roles = roleRepository.findById(update.getRoleid());

        membersRepository.updateMemberRole(update.getRoleid(), update.getMemberId());


        return ResponseEntity.ok(new MessageResponse("Member  "  +  update.getMemberId()  + " With Role  " +  roles.get().getName()  + "  Updated successfully!"));
    }

    @PutMapping(path = "/unlock/{id}")
    public ApiResponse unlock(@PathVariable Long id) {
        ApiResponse response = new ApiResponse();
        Optional<Members> members = membersRepository.findById(id);
        if (members.isPresent()) {
//            check if account is active
            Members members1 = members.get();
            members1.setAcctLocked(false);
            membersRepository.save(members1);
            response.setMessage("UnLocked Successful!");
            response.setStatusCode(HttpStatus.OK.value());
            return response;
        } else {
            response.setMessage("User Not Found");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return response;
        }
    }

    @PutMapping(path = "/logout/{id}")
    public ApiResponse logout(@PathVariable Long id) {
        ApiResponse response = new ApiResponse();
        Optional<Members> members = membersRepository.findById(id);
        if (members.isPresent()) {
//            check if account is active
            Members members1 = members.get();
            members1.setActive(false);
            membersRepository.save(members1);
            response.setMessage("Logout Successful!");
            response.setStatusCode(HttpStatus.OK.value());
            return response;
        } else {
            response.setMessage("User Not Found");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return response;
        }
    }

    @PutMapping(path = "/restore/{id}")
    public ResponseEntity<?> restore(@PathVariable Long id) {
        ApiResponse response = new ApiResponse();
        Optional<Members> members = membersRepository.findById(id);
        if (members.isPresent()) {
//            check if account is active
            Members members1 = members.get();
            members1.setActive(true);
            members1.setDeletedFlag("N");
//            members1.setDeleteFlag("N");
//            user.setVerifiedFlag('N');
//            user.setModifiedFlag('Y');
//            user.setModifiedBy(UserRequestContext.getCurrentUser());
//            user.setModifiedTime(new Date());
            membersRepository.save(members1);
            response.setMessage("Unlocked Successful!");
            response.setStatusCode(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("User Not Found");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    //    If is active
    @DeleteMapping(path = "/delete/{memberNumber}")
    public ApiResponse delete(@PathVariable String memberNumber) {
        ApiResponse response = new ApiResponse();

        Optional<Members> members = membersRepository.findByMemberNumber(memberNumber);
        if (members.isPresent()) {
            Boolean isSuperuser = false;
            Boolean isManager = false;

            for (Role role : members.get().getRoles()) {
                if (role.getName().equalsIgnoreCase(String.valueOf(ERole.ROLE_SUPERUSER))) {
                    isSuperuser = true;
                } else if (role.getName().equalsIgnoreCase(String.valueOf(ERole.ROLE_ADMIN))) {
                    isManager = true;
                }
            }

            if (isSuperuser) {
                response.setMessage("Superuser cannot be deleted");
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                return response;
            } else {
                if (isManager) {
                    response.setMessage("ChurchAdmin cannot be deleted");
                    response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                    return response;
                } else {
                    Members memberToDelete = members.get();
                    memberToDelete.setActive(false);
                    memberToDelete.setDeletedFlag("Y");
                    memberToDelete.setDeletedBy(UserRequestContext.getCurrentUser());
                    memberToDelete.setDeletedTime(new Date());
                    membersRepository.save(memberToDelete);
                    response.setMessage("Member Deleted");
                    response.setStatusCode(HttpStatus.OK.value());
                    return response;
                }
            }
        } else {
            response.setMessage("Member Not Found");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return response;
        }
    }
    String modified_by ="";

    String modified_on = dtf.format(now);

    @PutMapping("/deleteaccount")
    public ResponseEntity<?> deleteAccount(@Valid @RequestBody DeleteAccount update) {
        modified_by ="Admin";

        //Delete Flag
        String delete_flag = "Y";
        boolean isActive = false;


        membersRepository.deleteUserAccount(isActive, delete_flag, modified_on, modified_by, update.getMemberNumber());


        return ResponseEntity.ok(new MessageResponse("Member Account Deleted successfully!"));
    }



    @GetMapping(path = "/find/by/memberNumber/{member}")
    public ApiResponse getMemberByMemberNumber(@PathVariable String member) {
        ApiResponse response = new ApiResponse<>();
        Optional<Members> members1 = membersRepository.findByMemberNumber(member);
        if (members1.isPresent()) {
            Members members = members1.get();
            response.setMessage(HttpStatus.FOUND.getReasonPhrase());
            response.setStatusCode(HttpStatus.FOUND.value());
            response.setEntity(members);
            return response;
//            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("MemberNumber not found");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return response;
        }


    }

    @GetMapping(path = "/memberdetails/findbyNumber/{number}")
    public ApiResponse getMemberByNumber(@PathVariable String number) {
        ApiResponse response = new ApiResponse<>();
        Optional<MemberDetails> members1 = membersRepository.searchByNumber(number);
        if (members1.isPresent()) {
            MemberDetails members = members1.get();
            response.setMessage(HttpStatus.FOUND.getReasonPhrase());
            response.setStatusCode(HttpStatus.FOUND.value());
            response.setEntity(members);
            return response;
//            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("MemberNumber not found");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return response;
        }



    }   @GetMapping(path = "/systemUsersDetails")
    public ApiResponse getSystemUsers() {
        ApiResponse response = new ApiResponse<>();
        List<MemberDetails> members1 = membersRepository.getAllUsers();
        if (!members1.isEmpty()) {

            response.setMessage(HttpStatus.FOUND.getReasonPhrase());
            response.setStatusCode(HttpStatus.FOUND.value());
            response.setEntity(members1);
            return response;
//            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Members not found");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return response;
        }



    }
    @GetMapping(path = "/find/by/appId/{appId}")
    public ApiResponse getMemberByAppUser(@PathVariable Long appId) {
        ApiResponse response = new ApiResponse<>();
        Optional<Members> members1 = membersRepository.findByAppId(appId);
        if (members1.isPresent()) {
            Members members = members1.get();
            response.setMessage(HttpStatus.FOUND.getReasonPhrase());
            response.setStatusCode(HttpStatus.FOUND.value());
            response.setEntity(members);
            return response;
//            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("AppUser not found");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return response;
        }

    }

    @GetMapping(path = "memberDetails/find/by/groupsId/{groupsId}")
    public ApiResponse getMemberByGroupsId(@PathVariable Long groupsId) {
        ApiResponse response = new ApiResponse<>();
        List<MemberDetails> members1 = membersRepository.searchByGroupsId(groupsId);
        if (!members1.isEmpty()) {
            response.setMessage(HttpStatus.FOUND.getReasonPhrase());
            response.setStatusCode(HttpStatus.FOUND.value());
            response.setEntity(members1);
            return response;
        } else {
            response.setMessage("Member not found");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return response;
        }

    }  @GetMapping(path = "memberDetails/find/by/postedTime")
    public ApiResponse getMemberByDateRange(@RequestParam Date fromDate, @RequestParam Date toDate ) {
        ApiResponse response = new ApiResponse<>();
        List<MemberDetails> members1 = membersRepository.searchByDateRange(datesCalculator.dateFormat(fromDate), datesCalculator.dateFormat(toDate));
        if (!members1.isEmpty()) {
            response.setMessage(HttpStatus.FOUND.getReasonPhrase());
            response.setStatusCode(HttpStatus.FOUND.value());
            response.setEntity(members1);
            return response;
        } else {
            response.setMessage("Member not found");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return response;
        }

    }

    @GetMapping(path = "memberDetails/find/by/formData")
    public ApiResponse getMemberByFormData( @RequestParam String memberNumber, @RequestParam String phoneNumber ) {
        ApiResponse response = new ApiResponse<>();
        List<MemberDetails> members1 = membersRepository.searchByFormData(memberNumber,phoneNumber);
        if (!members1.isEmpty()) {
            MemberDetails  members = members1.get(0);



            response.setMessage("Member " + members.getFirstname() + " " + members.getLastname() + " " + members.getFirstname() +  " " + members.getNumber() + " Found!");
            response.setStatusCode(HttpStatus.OK.value());
            response.setEntity(members1);

            return response;
        } else {
            response.setMessage("Member not found");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return response;
        }

    }

    @GetMapping(path = "memberDetails/find/by/communityId/{communityId}")
    public ApiResponse getMemberByCommunityId(@PathVariable Long communityId) {
        ApiResponse response = new ApiResponse<>();
        List<MemberDetails> members1 = membersRepository.searchByCommunityId(communityId);
        if (!members1.isEmpty()) {
            response.setMessage(HttpStatus.FOUND.getReasonPhrase());
            response.setStatusCode(HttpStatus.FOUND.value());
            response.setEntity(members1);
            return response;
        } else {
            response.setMessage("Member not found");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return response;
        }

    }

    @GetMapping(path = "memberDetails/find/by/outstationId/{outstationId}")
    public ApiResponse getMemberByOutstationId(@PathVariable Long outstationId) {
        ApiResponse response = new ApiResponse<>();
        List<MemberDetails> members1 = membersRepository.searchByOutstationId(outstationId);
        if (!members1.isEmpty()) {
            response.setMessage(HttpStatus.FOUND.getReasonPhrase());
            response.setStatusCode(HttpStatus.FOUND.value());
            response.setEntity(members1);
            return response;
        } else {
            response.setMessage("Member not found");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return response;
        }

    }

    @GetMapping(path = "/members")
    public List<Members> allMembers() {
        return membersRepository.allRoleMember();
    }

//    @GetMapping(path = "/all/members")
//    public List<MemberDetails> getAllMembers() {
//        return membersRepository.getAllMembers();
//    }
    public String generateMemberNumber() {
        String newMemberNo = "";
        Optional<Members> memberNumber = membersRepository.getMemberNumber();
        // Retrieve the maximum existing member number from the database
        if (memberNumber.isPresent()) {
            log.info("Increment  by 1");
            String memberNo = memberNumber.get().getMemberNumber();
            String suffix = memberNo.substring(2);
            int suffixNo = Integer.parseInt(suffix);
            String formattedCode = String.format("%05d", suffixNo + 1);
            newMemberNo = "EM" + formattedCode;
        } else {
            newMemberNo = "EM00001";
        }
        return newMemberNo;

    }
    @GetMapping(path = "/all/members")
    public List<MemberDetails> getAllActiveMembers() {
        return membersRepository.getAllActiveMembers();
    }

    @GetMapping(path = "/all/members/disability")
    public List<MemberDetails> getAllMemberswithDisability() {
        return membersRepository.getAllDisabledMembers();
    }
    @GetMapping(path = "/all/members/deleted")
    public List<MemberDetails> getAllDeletedMembers() {
        return membersRepository.getAllDeletedMembers();
    }

    @GetMapping(path = "/all/members/locked")
    public List<MemberDetails> getAllLockedMembers() {
        return membersRepository.getAllLockedMembers();
    }






    public String generateFamily() {
        String newFamilyNo = "";
        Optional<Family> familyNumber = frepo.getFamilyNumber();
        // Retrieve the maximum existing family number from the database
        if (familyNumber.isPresent()) {
            log.info("Increment  by 1");
            String familyNo = familyNumber.get().getFamilyNumber();
            String suffix = familyNo.substring(2);
            int suffixNo = Integer.parseInt(suffix);
            String formattedCode = String.format("%05d", suffixNo + 1);
            newFamilyNo = "FN" + formattedCode;
        } else {
            newFamilyNo = "FN00001";
        }
        return newFamilyNo;

    }


    public  String convertPhoneNumber(String phoneNumber) {
        // Check if the number starts with '0' (indicating a Kenyan number).
        if (phoneNumber != null && phoneNumber.startsWith("0")) {
            // Remove the leading '0' and add the country code '+254'.
            return "+254" + phoneNumber.substring(1);
        }
        // If the number is already in international format, return it as is.
        return phoneNumber;
    }



}
