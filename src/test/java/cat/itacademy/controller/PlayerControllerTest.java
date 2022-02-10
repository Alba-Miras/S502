package cat.itacademy.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import cat.itacademy.dto.PlayerDto;
import cat.itacademy.service.PlayerService;

@TestMethodOrder(OrderAnnotation.class)
@WebMvcTest(controllers = PlayerController.class)
@WithMockUser(username = "admin", password = "random", authorities= "ROLE_USER")
class PlayerControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserDetailsService userDetailsService;
	
	@MockBean
	private BCryptPasswordEncoder encoder;

	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private PlayerService playerService;
	
	private final Long ID = 1L;

	@Test
	@Order(1)
	void testAddPlayer() throws Exception {
		PlayerDto playerDto = new PlayerDto();
		playerDto.setName("Player 1");
		when(playerService.addPlayer(any(PlayerDto.class))).thenReturn(playerDto);
		this.mockMvc.perform(post("/players")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(playerDto)))
			.andExpect(status().isCreated())
			.andExpect(MockMvcResultMatchers.jsonPath(".name").value("Player 1"));
		verify(playerService, times(1)).addPlayer(any(PlayerDto.class));
	}

	@Test
	@Order(2)
	void testUpdatePlayerName() throws Exception {
		PlayerDto playerDto = new PlayerDto();
		playerDto.setName("anònim");
		PlayerDto newPlayerDto = new PlayerDto();
		newPlayerDto.setName("Anònim");
		when(playerService.updatePlayerName(
			any(Long.class),
			any(PlayerDto.class))
		).thenReturn(newPlayerDto);
		this.mockMvc.perform(put("/players/{id}", ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(playerDto)))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath(".name").value("Anònim"));
		verify(playerService, times(1))
			.updatePlayerName(any(Long.class), any(PlayerDto.class));
	}

	@Test
	@Order(3)
	void testGetPlayersNameAndSuccessRate() throws Exception {
		when(playerService.getPlayersAndAverageScore()).thenReturn("Players:\n");
		this.mockMvc.perform(get("/players"))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.content().string("Players:\n"));
		verify(playerService, times(1)).getPlayersAndAverageScore();
	}

}
