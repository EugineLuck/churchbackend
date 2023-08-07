package co.ke.emtechhouse.es.utils.Security.jwt;

import co.ke.emtechhouse.es.Auth.Security.jwt.JwtUtils;
import co.ke.emtechhouse.es.Auth.Security.services.UserDetailsServiceImpl;
import co.ke.emtechhouse.es.utils.HttpInterceptor.AccessTokenContext;
import co.ke.emtechhouse.es.utils.HttpInterceptor.EntityRequestContext;
import co.ke.emtechhouse.es.utils.HttpInterceptor.UserDetailsRequestContext;
import co.ke.emtechhouse.es.utils.HttpInterceptor.UserRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String  userName = request.getHeader("userName");
			String  entityId = request.getHeader("entityId");
			String accessToken = request.getHeader("accessToken");
			String currentUserDetails = request.getHeader("userDetails");
			UserRequestContext.setCurrentUser(userName);
			EntityRequestContext.setCurrentEntityId(entityId);
			AccessTokenContext.setCurrentAccessToken(accessToken);
			UserDetailsRequestContext.setCurrentUserDetails(currentUserDetails);
			String jwt = accessToken;
			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				String username = jwtUtils.getUserNameFromJwtToken(jwt);
				UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			logger.error("Cannot set user authentication: {}", e);
		}
		filterChain.doFilter(request, response);
	}

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}
		return null;
	}
}
