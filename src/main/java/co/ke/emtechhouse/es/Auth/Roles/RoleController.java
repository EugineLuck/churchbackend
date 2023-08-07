package co.ke.emtechhouse.es.Auth.Roles;
import co.ke.emtechhouse.es.Auth.utils.HttpInterceptor.UserRequestContext;
import co.ke.emtechhouse.es.Auth.utils.Response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600000)
@RestController
@RequestMapping("/auth/role")
@Slf4j
public class RoleController {
    private final RoleRepository roleRepository;
    private final RoleService roleService;

    public RoleController(RoleRepository roleRepository, RoleService roleService) {
        this.roleRepository = roleRepository;
        this.roleService = roleService;
    }
    @PostMapping("/add")
    public ResponseEntity<?> addRole(@RequestBody Role role) {
        try {
            ApiResponse response = new ApiResponse();
            String name = "ROLE_"+role.getName().toUpperCase();
            role.setName(name);
            Optional<Role> role1 = roleRepository.findByName(role.getName());
            if (role1.isPresent()) {
                response.setMessage("Role Exist! with the name "+role.getName());
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                response.setEntity(role);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                role.setPostedBy(UserRequestContext.getCurrentUser());
                role.setPostedFlag('Y');
                role.setPostedTime(new Date());
                Role newRole = roleService.addRole(role);
                response.setMessage(HttpStatus.CREATED.getReasonPhrase());
                response.setStatusCode(HttpStatus.CREATED.value());
                response.setEntity(newRole);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllRoles() {
        try {
            ApiResponse response = new ApiResponse();
            List<Role> role = roleRepository.findByDeletedFlag('N');
            response.setMessage(HttpStatus.OK.getReasonPhrase());
            response.setStatusCode(HttpStatus.OK.value());
            response.setEntity(role);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable("id") Long id) {
        try {
            ApiResponse response = new ApiResponse();
            Role role = roleService.findById(id);
            response.setMessage(HttpStatus.OK.getReasonPhrase());
            response.setStatusCode(HttpStatus.OK.value());
            response.setEntity(role);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    @PutMapping("/modify")
    public ResponseEntity<?> updateRole(@RequestBody Role role) {
        try {
            ApiResponse response = new ApiResponse();
            role.setModifiedBy(UserRequestContext.getCurrentUser());
            Optional<Role> role1 = roleRepository.findByName(role.getName());
            if (role1.isPresent()) {
                role.setPostedTime(role1.get().getPostedTime());
                role.setPostedFlag('Y');
                role.setPostedBy(role1.get().getPostedBy());
                role.setModifiedFlag('Y');
                role.setVerifiedFlag('N');
                role.setModifiedTime(new Date());
                role.setModifiedBy(role.getModifiedBy());
                roleService.updateRole(role);
                response.setMessage(HttpStatus.OK.getReasonPhrase());
                response.setStatusCode(HttpStatus.OK.value());
                response.setEntity(role);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setEntity("");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @PutMapping("/verify/{id}")
    public ResponseEntity<?> verify(@PathVariable Long id) {
        try {
            ApiResponse response = new ApiResponse();
            Optional<Role> role1 = roleRepository.findById(id);
            if (role1.isPresent()) {
                Role role = role1.get();
                //                    Check Maker Checker
                if (role.getPostedBy().equalsIgnoreCase(UserRequestContext.getCurrentUser())){
                    response.setMessage("You Can Not Verify What you initiated");
                    response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
                    response.setEntity("");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }else{
                    role.setVerifiedFlag('Y');
                    role.setVerifiedTime(new Date());
                    role.setVerifiedBy(UserRequestContext.getCurrentUser());
                    roleRepository.save(role);
                    response.setMessage(HttpStatus.OK.getReasonPhrase());
                    response.setStatusCode(HttpStatus.OK.value());
                    response.setEntity(role);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            } else {
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setEntity("");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        try {
            ApiResponse response = new ApiResponse();
            Optional<Role> role1 = roleRepository.findById(id);
            if (role1.isPresent()) {
                Role role = role1.get();
                role.setDeletedFlag('Y');
                role.setDeletedTime(new Date());
                role.setDeletedBy(UserRequestContext.getCurrentUser());
                roleRepository.save(role);
                response.setMessage(HttpStatus.OK.getReasonPhrase());
                response.setStatusCode(HttpStatus.OK.value());
                response.setEntity(role);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setEntity("");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }
}
