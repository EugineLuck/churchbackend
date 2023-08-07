package co.ke.emtechhouse.es;

//import co.ke.emtechhouse.es.Auth.DTO.Mailparams;
import co.ke.emtechhouse.es.AppUser.AppUser;
import co.ke.emtechhouse.es.AppUser.AppUserRepo;
import co.ke.emtechhouse.es.AppUser.AppUserService;
import co.ke.emtechhouse.es.Auth.Roles.ERole;
import co.ke.emtechhouse.es.Auth.Roles.Role;
import co.ke.emtechhouse.es.Auth.Roles.RoleRepository;
import co.ke.emtechhouse.es.Auth.Members.Members;
import co.ke.emtechhouse.es.Auth.Members.MembersRepository;
import co.ke.emtechhouse.es.Auth.Members.MembersService;
import co.ke.emtechhouse.es.MpesaIntergration.Services.AcknowledgeResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SpringBootApplication
public class ChurchApplication {
	@Autowired
	private MembersRepository membersRepository;

	@Autowired
	private MembersService membersService;
	@Autowired
	private AppUserRepo appUserRepo;
	@Autowired
	private  AppUserService appUserService;
	@Autowired
	PasswordEncoder encoder;

	@Autowired
	private RoleRepository roleRepository;

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();
	String modified_on = dtf.format(now);


	@Value("${organisation.superUserEmail}")
	private String superUserEmail;
	@Value("${organisation.superUserFirstName}")
	private String superUserFirstName;
	@Value("${organisation.superUserLastName}")
	private String superUserLastName;

	@Value("${organisation.superUserUserName}")
	private String superUserUserName;
	@Value("${organisation.superUserPhone}")
	private String superUserPhone;
	@Value("${organisation.superUserCommunity}")
	private Long superUserCommunity;
	@Value("${organisation.superUserSolCode}")
	private String superUserSolCode;
	@Value("${organisation.superUserPassword}")
	private String superUserPassword;

	public ChurchApplication(MembersService membersService) {
		this.membersService = membersService;
	}
	@Bean
	public ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}
	@Bean
	public OkHttpClient getOkHttpClient() {
		return new OkHttpClient();
	}
	@Bean
	public AcknowledgeResponse getAcknowledgeResponse(){
		AcknowledgeResponse acknowledgeResponse = new AcknowledgeResponse();
		acknowledgeResponse.setMessage("success");
		return acknowledgeResponse;
	}
	public static void main(String[] args) {
		SpringApplication.run(ChurchApplication.class, args);
	}
	@Bean
	CommandLineRunner runner() {
		return args -> {
			//Add Roles
			if (roleRepository.findAll().size() < 1) {
				List<Role> roleList = new ArrayList<>();
				Role adminRole = new Role();
				adminRole.setName(ERole.ROLE_ADMIN.toString());
				adminRole.setPostedBy("System");
				adminRole.setPostedFlag('Y');
				adminRole.setVerifiedBy("System");
				adminRole.setVerifiedFlag('Y');
				adminRole.setVerifiedTime(new Date());
				adminRole.setPostedTime(new Date());

				Role memberRole = new Role();
				memberRole.setName(ERole.ROLE_MEMBER.toString());
				memberRole.setPostedBy("System");
				memberRole.setPostedFlag('Y');
				memberRole.setVerifiedBy("System");
				memberRole.setVerifiedFlag('Y');
				memberRole.setVerifiedTime(new Date());
				memberRole.setPostedTime(new Date());

				Role secretaryRole = new Role();
				secretaryRole.setName(ERole.ROLE_SECRETARY.toString());
				secretaryRole.setPostedBy("System");
				secretaryRole.setPostedFlag('Y');
				secretaryRole.setVerifiedBy("System");
				secretaryRole.setVerifiedFlag('Y');
				secretaryRole.setVerifiedTime(new Date());
				secretaryRole.setPostedTime(new Date());

				Role appUserRole = new Role();
				appUserRole.setName(ERole.ROLE_APP_USER.toString());
				appUserRole.setPostedBy("System");
				appUserRole.setPostedFlag('Y');
				appUserRole.setVerifiedBy("System");
				appUserRole.setVerifiedFlag('Y');
				appUserRole.setVerifiedTime(new Date());
				appUserRole.setPostedTime(new Date());


				Role treasurerRole = new Role();
				treasurerRole.setName(ERole.ROLE_TREASURER.toString());
				treasurerRole.setPostedBy("System");
				treasurerRole.setPostedFlag('Y');
				treasurerRole.setVerifiedBy("System");
				treasurerRole.setVerifiedFlag('Y');
				treasurerRole.setVerifiedTime(new Date());
				treasurerRole.setPostedTime(new Date());
//					save
				roleList.add(memberRole);
				roleList.add(secretaryRole);
				roleList.add(treasurerRole);
				roleList.add(adminRole);
				roleList.add(appUserRole);
				roleRepository.saveAll(roleList);
			}
			//Create super admin for new system installation and database initialization
			if (appUserRepo.findByUserName(superUserUserName).isEmpty()) {
				Set<Role> roles = new HashSet<>();
				Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN.toString())
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(userRole);
				Set<String> suRoles = new HashSet<>();
				AppUser appUser = new AppUser();
				appUser.setUserName(superUserUserName);
				suRoles.add(ERole.ROLE_ADMIN.toString());
				appUser.setRoles(roles);
				appUser.setFirstLogin('Y');
				appUser.setPassword(encoder.encode(superUserPassword));
				appUserService.appUserRegistration(appUser);


				String mailMessage = "Dear " + appUser.getUserName() + " your account has been successfully created using username  Admin" +
						" and password " + superUserPassword + " Login in to change your password.";
				System.out.println(mailMessage);
//				mailService.sendEmail(user.getEmail(), mailMessage, "Account Successfully Created");
//				Mailparams mailsample = new Mailparams();
//				mailsample.setEmail(members.getEmail());
//				mailsample.setSubject("Account Successfully Created");
//				mailsample.setMessage(mailMessage);
			}
		};
	}
}
