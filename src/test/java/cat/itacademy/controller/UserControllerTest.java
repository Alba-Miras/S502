package cat.itacademy.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import cat.itacademy.dto.UserDto;
import cat.itacademy.service.UserService;

@TestMethodOrder(OrderAnnotation.class)
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
//	@MockBean
//	private UserDetailsService userDetailsService;

//	@MockBean
//	private BCryptPasswordEncoder encoder;

	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private UserService userService;

	@Test
	@Order(1)
	void testAddUser() throws Exception {
		UserDto userDto = new UserDto();
		userDto.setUserName("user");
		userDto.setPassword("password");
		when(userService.addUser(any(UserDto.class)))
			.thenReturn(userDto.getUserName());
		this.mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userDto)))
			.andExpect(status().isCreated())
			.andExpect(MockMvcResultMatchers
				.content().string(userDto.getUserName()));
		verify(userService, times(1)).addUser(any(UserDto.class));
	}

}
