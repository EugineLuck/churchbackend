package co.ke.emtechhouse.es.USSD;


import co.ke.emtechhouse.es.Announcements.Announcements;
import co.ke.emtechhouse.es.Announcements.AnnouncementsRepo;
import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Auth.Members.MembersRepository;
import co.ke.emtechhouse.es.Auth.Roles.ERole;
import co.ke.emtechhouse.es.Auth.Roles.Role;
import co.ke.emtechhouse.es.Auth.Roles.RoleRepository;
import co.ke.emtechhouse.es.Community.Community;
import co.ke.emtechhouse.es.Community.CommunityRepository;
import co.ke.emtechhouse.es.Deanery.DeaneryRepository;
import co.ke.emtechhouse.es.Diocese.DioceseRepository;
import co.ke.emtechhouse.es.Family.Family;
import co.ke.emtechhouse.es.Family.FamilyRepository;
import co.ke.emtechhouse.es.Giving.Giving;
import co.ke.emtechhouse.es.Giving.GivingRepo;
import co.ke.emtechhouse.es.Groups.Groups;
import co.ke.emtechhouse.es.Groups.GroupsRepo;
//import co.ke.emtechhouse.es.NotificationComponent.NotificationRepo;
import co.ke.emtechhouse.es.MpesaIntergration.Mpesa_Express.InternalStkPushRequest;
import co.ke.emtechhouse.es.MpesaIntergration.Mpesa_Express.StkPushSyncResponse;
import co.ke.emtechhouse.es.MpesaIntergration.Services.DarajaApiImpl;
import co.ke.emtechhouse.es.MpesaIntergration.SuccessfullyTransactions;
import co.ke.emtechhouse.es.MpesaIntergration.Transaction;
import co.ke.emtechhouse.es.MpesaIntergration.TransactionRepo;
import co.ke.emtechhouse.es.OutStation.OutStation;
import co.ke.emtechhouse.es.OutStation.OutStationRepository;
import co.ke.emtechhouse.es.Parish.ParishRepository;
import co.ke.emtechhouse.es.PersonalDetails.PersonalDetailsRepo;
import co.ke.emtechhouse.es.SmsComponent.Emtech.Dtos.Dtos.SmsDto;
import co.ke.emtechhouse.es.SmsComponent.Emtech.Dtos.EmtSmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class USSDChecker {
    @Autowired
    private USSDRepo ussdRepo;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private EmtSmsService emtSmsService;

    private String termsAndConditions;
    @Autowired
    private DioceseRepository dioceseRepository;
    @Autowired
    private AnnouncementsRepo announcementsRepo;
    @Autowired
    private DeaneryRepository deaneryRepository;
    //    @Autowired
//    private NotificationRepo notificationRepo;
    @Autowired
    private ParishRepository parishRepository;
    @Autowired
    private OutStationRepository outStationRepository;
    @Autowired
    private PersonalDetailsRepo personalDetailsRepo;
    @Autowired
    private MembersRepository membersRepository;
    @Autowired
    private FamilyRepository familyRepository;
    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    DarajaApiImpl darajaImplementation;
    @Autowired
    private GroupsRepo groupsRepo;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    GivingRepo givingRepo;

    @Autowired
    TransactionRepo transactionRepo;



    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    String modified_on = dtf.format(now);
    public ResponseEntity<?> processUSSDRequests(String msisdn, String ussdString, String serviceCode, String sessionId) {

        Optional<USSD> details = this.ussdRepo.findByPhoneNumber(msisdn);

        StringBuilder sb = new StringBuilder();
        String response = "";
        USSD ussd = new USSD();
        Members members = new Members();

        Optional<Members>  currentMemberx =  membersRepository.findByPhoneNumber(getfomatedPhoneNumber(msisdn));
//        System.out.println("Checking number......" + getfomatedPhoneNumber(msisdn));
        String phone = "";
        Double amount = 0.0;
        String memberNumberx = "";
        Long churchCount = 0L;
        ussdString = ussdString.replace("*", "#");
        LinkedList<String> inputs = new LinkedList<>(Arrays.asList(ussdString.split("#")));



        if(inputs.size() == 1){

            log.info("Direct to menu");
            {
                response = "CON Welcome To EM -T CHURCH, Choose an option to continue!\n";
                response = response + "1. Register\n";
                response = response + "2. Church Giving\n";
                response = response + "3. Inquiry\n";
                response = response + "4. Update Details\n";
            }

        }else{


//            Handle Registration
            if(inputs.get(1).equals("1") && inputs.size() == 2) {

                response = "CON Lets start\n Enter National ID Number";
            }else if (inputs.get(1).equals("1") && inputs.size() == 3) {

                String nationalIdInput = inputs.get(2);

                try {
                    // Attempt to parse the input as a long
                    long nationalId = Long.parseLong(nationalIdInput);

                    // Check if it's a valid long
                    if (nationalId >= 0) {
                        response = "CON 2. Select Id Ownership Type \n";
                        response = response + "1. Individual\n";
                        response = response + "2. Parent/Guardian\n";
                    } else {
                        // Return to the previous step and ask for a valid National ID
                        response = "CON 1. Enter National Id\n";
                        response = response + "Invalid National ID. Please enter a valid National ID.";
                    }
                } catch (NumberFormatException e) {
                    // Return to the previous step and ask for a valid National ID
                    response = "CON 1. Enter National Id\n";
                    response = response + "Invalid input. Please enter a valid National ID.";
                }

            }else if (inputs.get(1).equals("1") && inputs.size() == 4) {
                String value = inputs.get(3);

                try {
                    // Attempt to parse the input as a long
                    long owner = Long.parseLong(value);

                    // Check if it's a valid long
                    if (owner >= 0 && owner <= 2) {
                        response = "CON 3. Enter First Name";
                    } else {
                        // Return to the previous step and ask for a valid National ID

                        response = "CON Invalid Option. Please enter a Try again.\n";
                        response = response + "Choose Owner Type\n";
                        response = response + "1. Individual\n";
                        response = response + "2. Parent/Guardian\n";

                    }
                } catch (NumberFormatException e) {
                    // Return to the previous step and ask for a valid National ID
                    response = "CON Invalid Option. Please enter a Try again.\n";
                    response = response + "Choose Owner Type\n";
                    response = response + "1. Individual\n";
                    response = response + "2. Parent/Guardian\n";
                }


            } else if (inputs.get(1).equals("1") && inputs.size() == 5) {

                String value = inputs.get(4);

                try {
                    // Attempt to parse the input as a long
                    long fname = Long.parseLong(value);

                    // Check if it's a valid long (owner type)
                    if (fname >= 0) {
                        response = "CON Invalid Option. Try again.\n";
                        response = response + " Enter First Name";
                    } else {
                        // Return to the previous step and ask for a valid owner type
                        response =  "CON Enter Last Name";

                    }
                } catch (NumberFormatException e) {
                    response =  "CON Enter Last Name";

                }


//                response = "CON 4. Enter Last Name";
            } else if (inputs.get(1).equals("1") && inputs.size() == 6) {
                String value = inputs.get(5);

                try {
                    // Attempt to parse the input as a long
                    long fname = Long.parseLong(value);

                    // Check if it's a valid long (owner type)
                    if (fname >= 0) {
                        response = "CON Invalid Option. Try again.\n";
                        response = response + " Enter Last Name";
                    } else {
                        response = "CON Select OutStation\n";
                        log.info("Updating details - display Out Station");
                        List<OutStation> outStations = this.outStationRepository.findAll();
                        if (outStations.size() > 0) {
                            int pageSize = 10; // Number of records per page
                            int totalPages = (outStations.size() + pageSize - 1) / pageSize; // Calculate total pages

                            int currentPage = 1;
                            if (inputs.size() > 6) {
                                currentPage = Integer.parseInt(inputs.get(6));
                            }

                            int startIndex = (currentPage - 1) * pageSize;
                            int endIndex = Math.min(startIndex + pageSize, outStations.size());

                            StringBuilder outStationList = new StringBuilder();
                            for (int i = startIndex; i < endIndex; i++) {
                                OutStation outStation = outStations.get(i);
                                outStationList.append("\n").append(outStation.getId()).append(". ").append(outStation.getOutStationName());
                            }

                            // Append options for navigating to the next page or saving data
                            if (currentPage < totalPages) {
                                outStationList.append("\n98. Next Page");
                            }

                            response = response + outStationList;

                        }

                    }
                } catch (NumberFormatException e) {
                    response = "CON Select OutStation\n";
                    log.info("Updating details - display Out Station");
                    List<OutStation> outStations = this.outStationRepository.findAll();
                    if (outStations.size() > 0) {
                        int pageSize = 10; // Number of records per page
                        int totalPages = (outStations.size() + pageSize - 1) / pageSize; // Calculate total pages

                        int currentPage = 1;
                        if (inputs.size() > 6) {
                            currentPage = Integer.parseInt(inputs.get(6));
                        }

                        int startIndex = (currentPage - 1) * pageSize;
                        int endIndex = Math.min(startIndex + pageSize, outStations.size());

                        StringBuilder outStationList = new StringBuilder();
                        for (int i = startIndex; i < endIndex; i++) {
                            OutStation outStation = outStations.get(i);
                            outStationList.append("\n").append(outStation.getId()).append(". ").append(outStation.getOutStationName());
                            churchCount++;
                        }

                        // Append options for navigating to the next page or saving data
                        if (currentPage < totalPages) {
                            outStationList.append("\n98. Next Page");
                        }


                        response = response + outStationList;

                    }

                }


            } else if (inputs.get(1).equals("1") && inputs.size() == 7) {

                String value = inputs.get(6);
                try {
                    long owner = Long.parseLong(value);
                    if (owner >= 0 ) {
//                        Start of Community
                        response = "CON 7. Select Community";
                        List<Community> communities = this.communityRepository.findAll();
                        if (communities.size() > 0) {
                            int pageSize = 10; // Number of records per page
                            int totalPages = (communities.size() + pageSize - 1) / pageSize; // Calculate total pages

                            int currentPage = 1;
                            if (inputs.size() > 7) {
                                currentPage = Integer.parseInt(inputs.get(7));
                            }

                            int startIndex = (currentPage - 1) * pageSize;
                            int endIndex = Math.min(startIndex + pageSize, communities.size());

                            StringBuilder communityList = new StringBuilder();
                            for (int i = startIndex; i < endIndex; i++) {
                                Community community = communities.get(i);
                                communityList.append("\n").append(community.getId()).append(". ").append(community.getCommunityName());
                            }

                            // Append options for navigating to the next page or saving data
                            if (currentPage < totalPages) {
                                communityList.append("\n98. Next Page");
                            }

                            response = "CON 8.  Select an Community\n" + communityList;

                        }

//                        End Of Community
                    } else {
                        // Return to the previous step and ask for a valid Oustation

                        response = "CON Select OutStation\n";
                        log.info("Updating details - display Out Station");
                        List<OutStation> outStations = this.outStationRepository.findAll();
                        if (outStations.size() > 0) {
                            int pageSize = 10; // Number of records per page
                            int totalPages = (outStations.size() + pageSize - 1) / pageSize; // Calculate total pages

                            int currentPage = 1;
                            if (inputs.size() > 6) {
                                currentPage = Integer.parseInt(inputs.get(6));
                            }

                            int startIndex = (currentPage - 1) * pageSize;
                            int endIndex = Math.min(startIndex + pageSize, outStations.size());

                            StringBuilder outStationList = new StringBuilder();
                            for (int i = startIndex; i < endIndex; i++) {
                                OutStation outStation = outStations.get(i);
                                outStationList.append("\n").append(outStation.getId()).append(". ").append(outStation.getOutStationName());
                                churchCount++;
                            }

                            // Append options for navigating to the next page or saving data
                            if (currentPage < totalPages) {
                                outStationList.append("\n98. Next Page");
                            }


                            response = response + outStationList;

                        }


                    }
                } catch (NumberFormatException e) {
                    // Return to the previous step and ask for a valid National ID
                    response = "CON Select OutStation\n";
                    log.info("Updating details - display Out Station");
                    List<OutStation> outStations = this.outStationRepository.findAll();
                    if (outStations.size() > 0) {
                        StringBuilder outStationList = new StringBuilder();
                        for (OutStation outStationx : outStations) {
                            outStationList.append("\n").append(outStationx.getId()).append(". ").append(outStationx.getOutStationName());
                            churchCount++;
                        }


                        response = response + outStationList;

                    }

                }



            } else if (inputs.get(1).equals("1") && inputs.size() == 8) {

                String value = inputs.get(7);
                try {
                    long owner = Long.parseLong(value);
                    if (owner >= 0 ) {
//
                        response = "CON 9. Select Groups";
                        log.info("Updating details - display Groups");
                        List<Groups> groups = groupsRepo.findAll();
                        if (groups.size() > 0) {
                            System.out.println(groups);
                            int pageSize = 10; // Number of records per page
                            int totalPages = (groups.size() + pageSize - 1) / pageSize; // Calculate total pages

                            int currentPage = 1;
                            if (inputs.size() > 8) {
                                currentPage = Integer.parseInt(inputs.get(8));
                            }

                            int startIndex = (currentPage - 1) * pageSize;
                            int endIndex = Math.min(startIndex + pageSize, groups.size());

                            StringBuilder groupsList = new StringBuilder();
                            for (int i = startIndex; i < endIndex; i++) {
                                Groups groups1 = groups.get(i);
                                groupsList.append("\n").append(groups1.getId()).append(". ").append(groups1.getGroupName());
                            }

                            // Append options for navigating to the next page or saving data
                            if (currentPage < totalPages) {
                                groupsList.append("\n98. Next Page");
                            }

                            response = "CON 10.  Select a Group\n" + groupsList;
                            System.out.println("checkkkkkkkkkkkkkkkkk" + response);
                        }
//                        End Of Community
                    } else {
                        // Return to the previous step and ask for a valid Oustation

//                        Start of Community
                        response = "CON 7. Invalid Choice. Try again.\nSelect Community\n";
                        List<Community> communities = this.communityRepository.findAll();
                        if (communities.size() > 0) {
                            int pageSize = 10; // Number of records per page
                            int totalPages = (communities.size() + pageSize - 1) / pageSize; // Calculate total pages

                            int currentPage = 1;
                            if (inputs.size() > 7) {
                                currentPage = Integer.parseInt(inputs.get(7));
                            }

                            int startIndex = (currentPage - 1) * pageSize;
                            int endIndex = Math.min(startIndex + pageSize, communities.size());

                            StringBuilder communityList = new StringBuilder();
                            for (int i = startIndex; i < endIndex; i++) {
                                Community community = communities.get(i);
                                communityList.append("\n").append(community.getId()).append(". ").append(community.getCommunityName());
                            }

                            // Append options for navigating to the next page or saving data
                            if (currentPage < totalPages) {
                                communityList.append("\n98. Next Page");
                            }

                            response = "CON 8.  Select an Community\n" + communityList;

                        }


                    }
                } catch (NumberFormatException e) {
                    // Return to the previous step and ask for a valid National ID
                    response = "CON 7. Invalid Choice. Try again.\nSelect Community\n";
                    log.info("Updating details - display Out Station");
                    List<Community> communities = this.communityRepository.findAll();
                    if (communities.size() > 0) {
                        StringBuilder communitiesList = new StringBuilder();
                        for (Community outStationx : communities) {
                            communitiesList.append("\n").append(outStationx.getId()).append(". ").append(outStationx.getCommunityName());
                            churchCount++;
                        }


                        response = response + communitiesList;

                    }

                }






            }
            else if (inputs.get(1).equals("1") && inputs.size() == 9) {


                String value = inputs.get(8);
                try {
                    long owner = Long.parseLong(value);
                    if (owner >= 0 ) {
//
                        response = "CON 11. Select Family\n";
                        log.info("Updating details - display Family");
                        List<Family> families = this.familyRepository.findAll();
                        if (families.size() > 0) {
                            int pageSize = 10; // Number of records per page
                            int totalPages = (families.size() + pageSize - 1) / pageSize; // Calculate total pages

                            int currentPage = 1;
                            if (inputs.size() > 9) {
                                currentPage = Integer.parseInt(inputs.get(9));
                            }

                            int startIndex = (currentPage - 1) * pageSize;
                            int endIndex = Math.min(startIndex + pageSize, families.size());

                            StringBuilder familyList = new StringBuilder();
                            for (int i = startIndex; i < endIndex; i++) {
                                Family family = families.get(i);
                                familyList.append("\n").append(family.getId()).append(". ").append(family.getFamilyName());
                            }

                            // Append options for navigating to the next page or saving data
                            if (currentPage < totalPages) {
                                familyList.append("\n98. Next Page");
                            }

                            response = response + familyList;

                        }
//                        End Of Community
                    } else {
                        // Return to the previous step and ask for a valid Oustation

//                        Start of Community
                        response = "CON 9. Select Groups\n";
                        log.info("Updating details - display Groups");
                        List<Groups> groups = groupsRepo.findAll();
                        if (groups.size() > 0) {
                            System.out.println(groups);
                            int pageSize = 10; // Number of records per page
                            int totalPages = (groups.size() + pageSize - 1) / pageSize; // Calculate total pages

                            int currentPage = 1;
                            if (inputs.size() > 8) {
                                currentPage = Integer.parseInt(inputs.get(8));
                            }

                            int startIndex = (currentPage - 1) * pageSize;
                            int endIndex = Math.min(startIndex + pageSize, groups.size());

                            StringBuilder groupsList = new StringBuilder();
                            for (int i = startIndex; i < endIndex; i++) {
                                Groups groups1 = groups.get(i);
                                groupsList.append("\n").append(groups1.getId()).append(". ").append(groups1.getGroupName());
                            }

                            // Append options for navigating to the next page or saving data
                            if (currentPage < totalPages) {
                                groupsList.append("\n98. Next Page");
                            }

                            response = response + groupsList;
                        }


                    }
                } catch (NumberFormatException e) {
                    // Return to the previous step and ask for a valid National ID
                    response = "CON 7. Invalid Choice. Try again.\nSelect Group\n";
                    List<Groups> groups = this.groupsRepo.findAll();
                    if (groups.size() > 0) {
                        StringBuilder groupList = new StringBuilder();
                        for (Groups groups1 : groups) {
                            groupList.append("\n").append(groups1.getId()).append(". ").append(groups1.getGroupName());
                            churchCount++;
                        }


                        response = response + groupList;

                    }

                }





            }else if (inputs.get(1).equals("1") && inputs.size() == 10) {
                String value = inputs.get(9);
                try {
                    long owner = Long.parseLong(value);
                    if (owner >= 0 ) {
//
                        response = "CON 13. Select Family Role \n";
                        response = response + "1. Father\n";
                        response = response + "2. Mother\n";
                        response = response + "3. Son\n";
                        response = response + "4. Daughter\n";
                        response = response + "5. Relative\n";

//                        End Of Community
                    } else {
                        // Return to the previous step and ask for a valid Oustation

                        response = "CON 7. Invalid Choice. Try again.\nSelect Group\n";
                        log.info("Updating details - display Family");
                        List<Family> families = this.familyRepository.findAll();
                        if (families.size() > 0) {
                            int pageSize = 10; // Number of records per page
                            int totalPages = (families.size() + pageSize - 1) / pageSize; // Calculate total pages

                            int currentPage = 1;
                            if (inputs.size() > 9) {
                                currentPage = Integer.parseInt(inputs.get(9));
                            }

                            int startIndex = (currentPage - 1) * pageSize;
                            int endIndex = Math.min(startIndex + pageSize, families.size());

                            StringBuilder familyList = new StringBuilder();
                            for (int i = startIndex; i < endIndex; i++) {
                                Family family = families.get(i);
                                familyList.append("\n").append(family.getId()).append(". ").append(family.getFamilyName());
                            }

                            // Append options for navigating to the next page or saving data
                            if (currentPage < totalPages) {
                                familyList.append("\n98. Next Page");
                            }

                            response = response + familyList;

                        }


                    }
                } catch (NumberFormatException e) {
                    // Return to the previous step and ask for a valid National ID
                    response = "CON 7. Invalid Choice. Try again.\nSelect Group\n";
                    List<Groups> groups = this.groupsRepo.findAll();
                    if (groups.size() > 0) {
                        StringBuilder groupList = new StringBuilder();
                        for (Groups groups1 : groups) {
                            groupList.append("\n").append(groups1.getId()).append(". ").append(groups1.getGroupName());
                            churchCount++;
                        }


                        response = response + groupList;

                    }

                }





            }

            else if (inputs.get(1).equals("1") && inputs.size() == 11) {
                if (inputs.get(1).equalsIgnoreCase("1")) {
                    Optional <USSD> member = ussdRepo.findByPhoneNumber(msisdn);
                    if (member.isPresent()){
                        response = "CON 14. Enter your phoneNumber";
                    }
                    else {

                        String memberNumber = generateMemberNumber();
                        log.info("{ " + msisdn + " }{ END Session }");


//Send Message With Link to terms and Conditions

                        USSD user = new USSD();
                        user.setFirstName(inputs.get(4));
                        user.setLastName(inputs.get(5));
                        user.setPhoneNumber(convertPhoneNumber(msisdn));
                        user.setOutStationId(Long.valueOf(Long.valueOf(inputs.get(6))));
                        user.setCommunityId(Long.valueOf(inputs.get(7)));
                        user.setGroupsId(Long.valueOf(inputs.get(8)));
                        user.setFamilyId(Long.valueOf(inputs.get(9)));
                        user.setIdOwnership(inputs.get(3));
                        user.setNationalID(inputs.get(2));
                        user.setMemberRole(inputs.get(10));
                        user.setMemberNumber(memberNumber);
                        ussdRepo.save(user);

                        Members members1 = new Members();
                        members1.setModeOfRegistration("USSD");
                        members1.setFirstName(inputs.get(4));
                        members1.setLastName(inputs.get(5));
                        members1.setIdOwnership(inputs.get(3));
                        members1.setNationalID(inputs.get(2));
                        members1.setMemberRole(inputs.get(10));

                        members1.setPhoneNumber(convertPhoneNumber(msisdn));
                        members1.setMemberNumber(memberNumber);
                        members1.setOutStationId(Long.valueOf(inputs.get(6)));
                        members1.setCommunityId(Long.valueOf(inputs.get(7)));
                        members1.setFamilyId(Long.valueOf(inputs.get(9)));

//                        members1.setPassword(encoder.encode("7777"));

                        Set<Groups> groups = new HashSet<>();

                        Groups memberGroups = groupsRepo.findById(Long.valueOf(inputs.get(6)))
                                .orElseThrow(() -> new RuntimeException("Error: Group is not found."));
                        groups.add(memberGroups);
//                        members1.setGroups(groups);

                        Set<Role> roles = new HashSet<>();

                        Role memberRoles = roleRepository.findById(Long.valueOf(1))
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(memberRoles);
//                        members1.setGroups(groups);
                        members1.setRoles(roles);
                        members1.setPostedTime(dtf.format(now));
                        membersRepository.save(members1);
                        response = "CONGRATULATIONS " + members1.getFirstName() + " ! " + "You have Successfully Registered to EMT Church.Your member number is " + memberNumber + ". Use your memberNumber" + memberNumber + " to login";

                        String message = "CONGRATULATIONS " + members1.getFirstName() + " ! " + "You have Successfully Registered to EMT Church.Your member number is " + memberNumber + ". Use your memberNumber " + memberNumber + " to login";
                        emtSmsService.sendSms(new SmsDto(msisdn, message));}
                }
            }
            else if (inputs.get(1).equals("1") && inputs.size() == 12) {
                if (inputs.get(1).equalsIgnoreCase("1")){
                    String memberNumber = generateMemberNumber();
                    log.info("{ " + msisdn + " }{ END Session }");

                    USSD user = new USSD();
                    user.setFirstName(inputs.get(2));
                    user.setLastName(inputs.get(3));
                    user.setPhoneNumber(convertPhoneNumber(inputs.get(8)));
                    user.setOutStationId(Long.valueOf(Long.valueOf(inputs.get(4))));
                    user.setCommunityId(Long.valueOf(inputs.get(5)));
                    user.setGroupsId(Long.valueOf(inputs.get(6)));
                    user.setFamilyId(Long.valueOf(inputs.get(7)));
                    user.setMemberNumber(memberNumber);
                    ussdRepo.save(user);

                    Members members1 = new Members();
                    members1.setModeOfRegistration("USSD");
                    members1.setFirstName(inputs.get(2));
                    members1.setLastName(inputs.get(3));
                    members1.setPhoneNumber(convertPhoneNumber(inputs.get(8)));
                    members1.setMemberNumber(memberNumber);
                    members1.setOutStationId(Long.valueOf(inputs.get(4)));
                    members1.setCommunityId(Long.valueOf(inputs.get(5)));
                    members1.setFamilyId(Long.valueOf(inputs.get(7)));
//                    members1.setPassword(encoder.encode("7777"));

                    Set<Groups> groups = new HashSet<>();

                    Groups memberGroups = groupsRepo.findById(Long.valueOf(inputs.get(6)))
                            .orElseThrow(() -> new RuntimeException("Error: Group is not found."));
                    groups.add(memberGroups);
//                    members1.setGroups(groups);

                    Set<Role> roles = new HashSet<>();

                    Role memberRoles = roleRepository.findById(Long.valueOf(1))
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(memberRoles);
//                    members1.setGroups(groups);
                    members1.setRoles(roles);
                    members1.setPostedTime(dtf.format(now));
                    membersRepository.save(members1);
                    response = "CONGRATULATIONS " + members1.getFirstName() + " ! " + "You have Successfully Registered to EMT Church.Your member number is " + memberNumber + ". Use your memberNumber" + memberNumber + " to login";

                    String message = "CONGRATULATIONS " + members1.getFirstName() + " ! " + "You have Successfully Registered to EMT Church.Your member number is " + memberNumber + ". Use your memberNumber " + memberNumber + " to login";
                    emtSmsService.sendSms(new SmsDto(msisdn, message));
                }
            }



            /**
             * Handle GIVING
             * Option
             * Menu List
             */
            else if(inputs.get(1).equals("2") && inputs.size() == 2) {
                List<Giving> giving = givingRepo.findAll();
                if (giving.size() > 0) {
                    System.out.println(giving);
                    int pageSize = 10; // Number of records per page
                    int totalPages = (giving.size() + pageSize - 1) / pageSize; // Calculate total pages

                    int currentPage = 1;
                    if (inputs.size() > 4) {
                        currentPage = Integer.parseInt(inputs.get(4));
                    }
                    int startIndex = (currentPage - 1) * pageSize;
                    int endIndex = Math.min(startIndex + pageSize, giving.size());
                    StringBuilder givingList = new StringBuilder();
                    for (int i = startIndex; i < endIndex; i++) {
                        Giving giving1 = giving.get(i);
                        givingList.append("\n").append(giving1.getId()).append(". ").append(giving1.getGivingTitle());
                    }
                    if (currentPage < totalPages) {
                        givingList.append("\n98. Next Page");
                    }
                    response = "CON  Choose an Option\n" + givingList;
                } else {
                    response = "END No active givings";
                }
            }else if(inputs.get(1).equals("2") && inputs.size() == 3) {

                Optional<Giving> giving = givingRepo.findById(Long.valueOf(inputs.get(2)));
                if (giving.isPresent()) {
                    Giving existingGiving = giving.get();
                    response = response + "CON *Giving: " + existingGiving.getGivingTitle() + "\n";
                    response = response + "*Description: " + existingGiving.getDescription() + "\n";
                    response = response + "*Target Amount: " + existingGiving.getTargetAmount() + "\n";
                    response = response + "*Amount: " + existingGiving.getGivingTitle() + "\n";
                    response = response + "1. Give\n";
                }


            } else if (inputs.get(1).equals("2") && inputs.size() == 4) {
                response = "CON Choose Option\n";

                if (currentMemberx.isPresent()) {
                    Members mem = currentMemberx.get();
                    response = "1. " + mem.getPhoneNumber() + "\n";

                }
                response = response + "2. Enter New Phone Number\n";
            } else if (inputs.get(1).equals("2") && inputs.size() == 5) {

                if(inputs.get(4).equals("1")){
                    Members mem = currentMemberx.get();
                    response = "CON Use "+ mem.getPhoneNumber() +"\n";
                    response = "1. Yes\n";
                }else{
                    response = response + "Enter Phone Number\n";
                }
                
            } else if (inputs.get(1).equals("2") && inputs.size() == 6) {
                if(currentMemberx.isPresent()) {
                    Members mem = currentMemberx.get();
                    response = "CON 1. "+ mem.getMemberNumber() +"\n";
                    response = response + "2. Enter Member Number\n";
                }else{
                    response = response + "2. Enter Member Number\n";
                }
                
            } else if (inputs.get(1).equals("2") && inputs.size() == 7) {

                response = "CON Choose Option\n";
                if(inputs.get(6).equals("1")){
                    if(currentMemberx.isPresent()) {
                        Members mem = currentMemberx.get();
                        response = response + "Use "+ mem.getMemberNumber() +"\n";
                        response = response+ "1. Yes\n";

                    }
                }else{
                    response = response + "Enter Member Number\n";

                }
                
            }else if(inputs.get(1).equals("2") && inputs.size() == 8){

                response = "CON Choose Option\n";
                Optional<Giving> giving = givingRepo.findById(Long.valueOf(inputs.get(2)));
                if(giving.isPresent()){
                    Giving existingGiving = giving.get();
                    response = response + "1. Amount: "+existingGiving.getAmount() +"\n";
                }
                response = response + "2. Enter New Amount";

            } else if (inputs.get(1).equals("2") && inputs.size() == 9) {
                response = "CON Choose Option\n";
                if(inputs.get(8).equals("1")){
                    response = "1. Yes";

                }else{
                    response = response + "Enter Amount\n";
                }

            } else if (inputs.get(1).equals("2") && inputs.size() == 10) {
//                Save Giving
                InternalStkPushRequest data = new InternalStkPushRequest();
                data.setTransactionAmount(Double.valueOf(amount));

                if(inputs.get(6).equals("1")){
                    Members mem = currentMemberx.get();
                    data.setMemberNumber(mem.getMemberNumber());
                }else{
                    data.setMemberNumber(inputs.get(7));

                }
                if(inputs.get(5).length() != 10){

                }
                if(inputs.get(4).equals("1")){
                    Members mem = currentMemberx.get();
                    data.setTransactionNumber(convertPhoneNumber(mem.getPhoneNumber()));
                }else{
                    if(inputs.get(5).length() < 10){
                        response = "END Enter a 10 digit Number 07xxxxxxxx";
                    }
                    data.setTransactionNumber(convertPhoneNumber(inputs.get(5)));
                }

                if(inputs.get(8).equals("1")){
                    Optional<Giving> giving = givingRepo.findById(Long.valueOf(inputs.get(2)));
                    if(giving.isPresent()){
                        Giving existingGiving = giving.get();
                        data.setTransactionAmount(Double.valueOf(existingGiving.getAmount()));
                    }
                }else{
                    data.setTransactionAmount(Double.valueOf(inputs.get(9)));
                }

                data.setGivingId(inputs.get(2));

                StkPushSyncResponse response1 = darajaImplementation.stkPushTransaction(data);
                if(response1.getResultCode().equals("0")){
                    response = "END Giving successfull";
                }else{
                    response = "END Failed\n";
                    response = response + response1.getResultDesc();
                }

                System.out.println(response1);


                
            }


            /**
             * Handle Inquery
             * Option
             * Menu List
             */

            else if(inputs.get(1).equals("3")){
                response = "CON Enquriy\n";

                if(inputs.size() == 2){
                    response = "Choose Option\n";
                    response = response + "1. My Details\n";
                    response = response + "2. Givings\n";
                    response = response + "3. Announcements\n";
                    
                } else if ( inputs.size() == 3 && inputs.get(2).equals("1")) {
                    response = "CON Enter Member Number";

                }else if ( inputs.size() == 4 && inputs.get(2).equals("1")) {
                    Optional existingMember = membersRepository.findByMemberNumber(inputs.get(3));
                    if(existingMember.isPresent()){
                        Members memDetails = (Members) existingMember.get();
                        response = "Dear " + memDetails.getFirstName() +", your request has been received. \n";
                        String message = "Kindly this is your account infomation.\n";
                        message = message + "Name: "+ memDetails.getFirstName() +" "+ memDetails.getLastName() +"\n";
                        message = message + "Phone Number: "+ memDetails.getPhoneNumber() +"\n";
                        message = message + "Gender: "+ memDetails.getGender() +" \n";
                        message = message + "Outstation: "+ getOutstaionName(memDetails.getOutStationId()) +" \n";
                        message = message + "Comminity: "+ getCommunityName(memDetails.getCommunityId())+ " \n";
                        message = message + "Family: "+ getFamilyName(memDetails.getFamilyId()) +"\n";
                        message = message + "National Id: "+ memDetails.getNationalID() +"\n";
                        response = response + message;
                        emtSmsService.sendSms(new SmsDto(msisdn, message));

                    }else{
                        response = "END Member Number not Found!";
                    }
                } else if (inputs.size() == 3 && inputs.get(2).equals("2")) {
                    response = "CON Enter Member Number";
                } else if (inputs.size() == 4 && inputs.get(2).equals("2")) {
                    Optional existingMember = membersRepository.findByMemberNumber(inputs.get(3));
                    if(existingMember.isPresent()){
                        Members memDetails = (Members) existingMember.get();
                        response = "CON Dear " + memDetails.getFirstName() +", your Givings.\n ";
                        List<SuccessfullyTransactions> allTransactions = transactionRepo.findByUssdMemberNumber(memDetails.getMemberNumber());
                        if(allTransactions.size() > 0){
                            int pageSize = 10; // Number of records per page
                            int totalPages = (allTransactions.size() + pageSize - 1) / pageSize; // Calculate total pages

                            int currentPage = 1;
                            if (inputs.size() > 4) {
                                currentPage = Integer.parseInt(inputs.get(4));
                            }
                            int startIndex = (currentPage - 1) * pageSize;
                            int endIndex = Math.min(startIndex + pageSize, allTransactions.size());

                            StringBuilder givingList = new StringBuilder();
                            for (int i = startIndex; i < endIndex; i++) {
                                SuccessfullyTransactions giving1 = allTransactions.get(i);
                                givingList.append("\n").append(giving1.getGivingId()).append(". ").append(giving1.getTitle());
                            }
                            if (currentPage < totalPages) {
                                givingList.append("\n98. Next Page");
                            }
                            response = response + givingList;
                            emtSmsService.sendSms(new SmsDto(msisdn, response));
                        }else{
                            response = "END No Givings found";
                        }
                    }else{
                        response = "END Member Number not Found!";
                    }
                    
                } else if (inputs.size() == 3 && inputs.get(2).equals("3")) {

                    response = "CON Announcements\n";

                    List<Announcements> allAnnouncements = announcementsRepo.findAll();
                    if(allAnnouncements.size() > 0){
                        int pageSize = 10; // Number of records per page
                        int totalPages = (allAnnouncements.size() + pageSize - 1) / pageSize; // Calculate total pages

                        int currentPage = 1;
                        if (inputs.size() > 4) {
                            currentPage = Integer.parseInt(inputs.get(4));
                        }
                        int startIndex = (currentPage - 1) * pageSize;
                        int endIndex = Math.min(startIndex + pageSize, allAnnouncements.size());

                        StringBuilder announcementsList = new StringBuilder();
                        for (int i = startIndex; i < endIndex; i++) {
                            Announcements announcement = allAnnouncements.get(i);
                            announcementsList.append("\n").append(announcement.getId()).append(". ").append(announcement.getTitle());
                        }
                        if (currentPage < totalPages) {
                            announcementsList.append("\n98. Next Page");
                        }
                        response = response + announcementsList;
                        emtSmsService.sendSms(new SmsDto(msisdn, response));
                    }else{
                        response = "END No Announcements found";
                    }
                    
                }




















//                Handle Member Update
            }else if(inputs.get(1).equals("4") && inputs.size() == 2){

                response = "CON Update Member Dateils\n";

            }else{
                response = "END Invalid Option";
            }


















        }




        return new ResponseEntity(response, HttpStatus.OK);
    }




    public  String getOutstaionName(Long id){
        Optional existingChurch = outStationRepository.findById(id);

        if(existingChurch.isPresent()){
            OutStation outStation = (OutStation) existingChurch.get();
            return outStation.getOutStationName();

        }
        return  null;
    }

    public  String getCommunityName(Long id){
        Optional existingCommunity = communityRepository.findById(id);

        if(existingCommunity.isPresent()){
            Community comm = (Community) existingCommunity.get();
            return comm.getCommunityName();

        }
        return  null;
    }

    public  String getFamilyName(Long id){
        Optional existingFamily = familyRepository.findById(id);

        if(existingFamily.isPresent()){
            Family family = (Family) existingFamily.get();
            return family.getFamilyName();

        }
        return  null;
    }



    //    GENERATING MEMBERNUMBER
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
    public String convertPhoneNumber(String phoneNumber) {
        // Check if the number starts with '0' (indicating a Kenyan number).
        if (phoneNumber != null && phoneNumber.length() == 10 && phoneNumber.startsWith("0")) {
            // Remove the leading '0' and add the country code '254'.
            return "254" + phoneNumber.substring(1);
        }
        // If the number is already in international format, return it as is.
        return phoneNumber;
    }

    public  String getfomatedPhoneNumber(String input) {
        // Ensure the input string is not null and has at least 9 characters
        if (input != null && input.length() >= 9) {
            // Use substring to get the last 9 characters
            return "254"+input.substring(input.length() - 9);
        } else {
            // Handle invalid input gracefully
            return "Invalid input";
        }
    }

}










