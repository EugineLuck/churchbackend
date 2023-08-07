package co.ke.emtechhouse.es.AppUser;


import co.ke.emtechhouse.es.Advertisement.Advertisement;
import co.ke.emtechhouse.es.Auth.Roles.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AppUserService {
    @Autowired
    AppUserRepo appUserRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public AppUser appUserRegistration(AppUser appUser)
    {
        try {
            AppUser savedAppUser = appUserRepo.save(appUser);
            System.out.println(savedAppUser);
            return savedAppUser;

        }catch (Exception e){
            log.info("Error {}: "+e);
            return null;
        }
    }

    public List<AppUser> getAll() {
        try {
            List<AppUser> users = appUserRepo.findAll();
            return users;
        }catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }

    }











}
