package cat.itacademy.security;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authenticationManager;
	
	public LoginAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	//Validate user name and password and create user with granted authorities
	@Override
	public Authentication attemptAuthentication(
		HttpServletRequest request, HttpServletResponse response) {
		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(
				request.getParameter("username"),
				request.getParameter("password")
			);
		return authenticationManager.authenticate(authenticationToken);
	}

	//Provide token if valid login
	@Override
	protected void successfulAuthentication(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain chain,
		Authentication authResult
	) throws IOException, ServletException {
		User user = (User) authResult.getPrincipal();
		String access_token = JWT.create()
			.withSubject(user.getUsername())
			.withExpiresAt(new Date(
				System.currentTimeMillis() + 60 * 60 * 1000)
			).withIssuer(request.getRequestURL().toString())
			.withClaim(
				"roles",
				user.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList())
			).sign(Algorithm.HMAC256("secretKey".getBytes()));
		response.setHeader("Authorization token", access_token);
		//Make token format more convenient
		String bearerToken = "Bearer " + access_token;
		Map<String, String> tokenMap = new HashMap<String, String>();
		tokenMap.put("token", bearerToken);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), tokenMap);
	}
	
}
