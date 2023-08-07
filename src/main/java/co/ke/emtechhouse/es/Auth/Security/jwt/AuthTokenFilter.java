package co.ke.emtechhouse.es.Auth.Security.jwt;

import co.ke.emtechhouse.es.AppUser.AppUserRepo;
import co.ke.emtechhouse.es.Auth.Security.services.UserDetailsServiceImpl;
import co.ke.emtechhouse.es.Auth.utils.HttpInterceptor.UserRequestContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private Clientinformation clientinformation;
	@Autowired
	private AppUserRepo appUserRepo;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			log.info("-------------------------------Authentication Entry---------------------");
			log.info("Requested URI: " +request.getRequestURI());
			log.info(String.valueOf("Request URL:"+request.getRequestURL()));
			String accessToken = request.getHeader("accessToken");
			String userName = request.getHeader("m");

			System.out.println(userName);
//			REMOVE
			System.out.println("**************************************************");
			UserRequestContext.setCurrentUser(userName);
//			Remove
			if (request.getRequestURI().startsWith("/auth/")){
				UserRequestContext.setCurrentUser("Guest");
			}

			String jwt = accessToken;
//			System.out.println(jwt);
			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				String member = jwtUtils.getUserNameFromJwtToken(jwt);
				System.out.println(member);
//				SET USER contact
				Optional<Object> appUser = appUserRepo.findByUserName(userName);
				clientinformation.getClientInformation(request, userName);
				System.out.println("Got called!");

				UserRequestContext.setCurrentUser(userName);
				UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			log.info("-------------------------------Authentication ends---------------------------");
		} catch (Exception e) {
			logger.error("Cannot set user authentication: {}", e);
		}

		filterChain.doFilter(request, response);
	}

//	private String parseJwjpjot(HttpServletRequest request) {
//		String headerAuth = request.getHeader("Authorization");
//		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
//			return headerAuth.substring(7, headerAuth.length());
//		}
//
//		return null;
//	}
}
