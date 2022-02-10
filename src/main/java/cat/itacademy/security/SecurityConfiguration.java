package cat.itacademy.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cat.itacademy.entity.RoleEnum;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Set authentication and authorization filters
		LoginAuthenticationFilter loginAuthenticationFilter =
			new LoginAuthenticationFilter(authenticationManagerBean());
		loginAuthenticationFilter.setFilterProcessesUrl("/users/login");
		http.csrf()
			.disable()
			.addFilter(loginAuthenticationFilter)
			.addFilterBefore(
				new JwtAuthorizationFilter(),
				UsernamePasswordAuthenticationFilter.class
			)
			//Set URLs restrictions
			.authorizeRequests()
				.antMatchers("/users/**").permitAll()
				.antMatchers("/players/ranking/**").permitAll()
				//Order matters, restrictions are not overridden
				.antMatchers(HttpMethod.DELETE, "/players/{playerId}/games")
					.hasAnyAuthority(RoleEnum.ROLE_ADMIN.toString())
				.antMatchers("/players/**")
					.hasAnyAuthority(
						RoleEnum.ROLE_ADMIN.toString(),
						RoleEnum.ROLE_USER.toString()
					)
				.anyRequest().authenticated();
		http.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
