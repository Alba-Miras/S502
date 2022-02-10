package cat.itacademy.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cat.itacademy.dto.UserDto;
import cat.itacademy.entity.RoleEnum;
import cat.itacademy.entity.User;
import cat.itacademy.repository.RoleRepository;
import cat.itacademy.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	//Validate user credentials against database, return Spring user with authorities
	@Override
	public UserDetails loadUserByUsername(String userName) {
		if (!userRepository.existsByUserName(userName)) {
			throw new UsernameNotFoundException("User not found.");
		}
		User user = userRepository.findByUserName(userName);
		List<SimpleGrantedAuthority> authorities =
			new ArrayList<SimpleGrantedAuthority>();
		user.getRoles().forEach(role -> {
			authorities.add(
				new SimpleGrantedAuthority(role.getRoleName().toString())
			);
		});
		return new org.springframework.security.core.userdetails.User(
			user.getUserName(),
			user.getPassword(),
			authorities
		);
	}
	
	//Add new user with user role if valid user name
	public String addUser(UserDto userDto) {
		if (userRepository.existsByUserName(userDto.getUserName())) {
			throw new EntityExistsException(
				"User with the same name already exists."
			);
		}
		User user = new User(
			userDto.getUserName(),
			encoder.encode(userDto.getPassword())
		);
		user.addRole(
			roleRepository.findByRoleName(RoleEnum.ROLE_USER)
				.orElseThrow(() -> new EntityNotFoundException("Role not found."))
		);
		return userRepository.save(user).getUserName();
	}

}
