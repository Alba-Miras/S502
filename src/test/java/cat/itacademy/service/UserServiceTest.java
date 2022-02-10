package cat.itacademy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import cat.itacademy.dto.UserDto;
import cat.itacademy.entity.Role;
import cat.itacademy.entity.RoleEnum;
import cat.itacademy.entity.User;
import cat.itacademy.repository.RoleRepository;
import cat.itacademy.repository.UserRepository;

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
class UserServiceTest {
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private RoleRepository roleRepository;
	
	@Mock
	private BCryptPasswordEncoder encoder;
	
	@InjectMocks
	private UserService userService;

	@Test
	@Order(1)
	void testLoadUserByUsername() {
		User user = new User("User", "password");
		user.addRole(new Role(RoleEnum.ROLE_USER));
		when(userRepository.existsByUserName(any(String.class))).thenReturn(true);
		when(userRepository.findByUserName(any(String.class))).thenReturn(user);
		
		UserDetails userDetails = userService.loadUserByUsername("username");
		assertEquals("User", userDetails.getUsername());
		assertEquals("password", userDetails.getPassword());
		assertEquals(
			"ROLE_USER",
			userDetails.getAuthorities().iterator().next().getAuthority()
		);
		verify(userRepository, times(1)).existsByUserName(any(String.class));
		verify(userRepository, times(1)).findByUserName(any(String.class));
	}
	
	@Test
	@Order(2)
	void testLoadUserByUsernameThrowsUsernameNotFoundException() {
		when(userRepository.existsByUserName(any(String.class))).thenReturn(false); 
		assertThrows(
			UsernameNotFoundException.class,
			() -> this.userService.loadUserByUsername("username")
		);
		verify(userRepository, times(1)).existsByUserName(any(String.class));
	}
	
	@Test
	@Order(3)
	void testAddUser() {
		UserDto userDto = new UserDto();
		userDto.setUserName("username");
		userDto.setPassword("password");
		Role role = new Role(RoleEnum.ROLE_USER);
		when(userRepository.existsByUserName(any(String.class))).thenReturn(false);
		when(encoder.encode(any(String.class))).thenReturn("encodedPassword");
		when(roleRepository.findByRoleName(any(RoleEnum.class))).thenReturn(Optional.of(role));
		when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
		
		assertEquals("username", userService.addUser(userDto));
		verify(userRepository, times(1)).existsByUserName(any(String.class));
		verify(roleRepository, times(1)).findByRoleName(any(RoleEnum.class));
		verify(encoder, times(1)).encode(any(String.class));
		ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
		verify(userRepository, times(1)).save(argument.capture());
		assertEquals("encodedPassword", argument.getValue().getPassword());		
	}
	
	@Test
	@Order(4)
	void testAddUserThrowsEntityExistsException() {
		UserDto userDto = new UserDto();
		userDto.setUserName("username");
		when(userRepository.existsByUserName(any(String.class))).thenReturn(true);
		assertThrows(
			EntityExistsException.class,
			() -> this.userService.addUser(userDto)
		);
		verify(userRepository, times(1)).existsByUserName(any(String.class));
	}
	
	@Test
	@Order(5)
	void testAddUserThrowsEntityNotFoundException() {
		UserDto userDto = new UserDto();
		userDto.setUserName("username");
		userDto.setPassword("password");
		when(userRepository.existsByUserName(any(String.class))).thenReturn(false);
		when(roleRepository.findByRoleName(any(RoleEnum.class))).thenReturn(Optional.empty());
		assertThrows(
				EntityNotFoundException.class,
			() -> this.userService.addUser(userDto)
		);
		verify(userRepository, times(1)).existsByUserName(any(String.class));
		verify(roleRepository, times(1)).findByRoleName(any(RoleEnum.class));
	}

}
