package co.ke.emtechhouse.es.Auth.Security.services;

import co.ke.emtechhouse.es.AppUser.AppUser;
import co.ke.emtechhouse.es.AppUser.AppUserRepo;
import co.ke.emtechhouse.es.Auth.utils.HttpInterceptor.UserDetailsRequestContext;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	AppUserRepo appUserRepo;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		AppUser user = (AppUser) appUserRepo.findByUserName(userName)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userName));
		    Gson g = new Gson();
			UserDetailsRequestContext.setCurrentUserDetails(g.toJson(user));
		return UserDetailsImpl.build(user);
	}
}
