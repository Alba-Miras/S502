package cat.itacademy.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

	//Validate token and pass authorization info
	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {
		String authorizationHeader =
				request.getHeader(HttpHeaders.AUTHORIZATION);
		//Check authorization need/format before token validation
		if (request.getServletPath().equals("users/login") ||
			authorizationHeader == null ||
			!authorizationHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		try {
			String token =
				authorizationHeader.substring("Bearer ".length());
			DecodedJWT decodedJwt = JWT.require(
				Algorithm.HMAC256("secretKey".getBytes())
			).build().verify(token);
			//Get user details from decoded token
			String username = decodedJwt.getSubject();
			List<String> roles = new ArrayList<String>();
			roles = decodedJwt.getClaim("roles").asList(String.class);
			List<SimpleGrantedAuthority> authorities =
				new ArrayList<SimpleGrantedAuthority>();
			roles.stream().forEach(
				role -> authorities.add(new SimpleGrantedAuthority(role))
			);
			//Pass user details to check user roles grant access to requested resource
			UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(username, null, authorities); 
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			filterChain.doFilter(request, response);
		} catch (Exception ex) {
			response.setHeader("error", ex.getMessage());
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
	}

}
