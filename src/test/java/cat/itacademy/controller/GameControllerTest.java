package cat.itacademy.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import cat.itacademy.dto.GameDto;
import cat.itacademy.service.GameService;

@TestMethodOrder(OrderAnnotation.class)
@WebMvcTest(controllers = GameController.class)
@WithMockUser(username = "admin", password = "random", authorities= "ROLE_USER")
class GameControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserDetailsService userDetailsService;
	
	@MockBean
	private BCryptPasswordEncoder encoder;

	@MockBean
	private GameService gameService;
	
	private final Long ID = 1L;
	
	@Test
	@Order(1)
	void testPlay() throws Exception {
		GameDto gameDto = new GameDto();
		gameDto.setVictory(true);
		when(gameService.play(any(Long.class))).thenReturn(gameDto);
		this.mockMvc.perform(post("/players/{id}/games", ID))
			.andExpect(MockMvcResultMatchers.jsonPath(".victory").value(true))
			.andExpect(status().isCreated());
		verify(gameService, times(1)).play(any(Long.class));
	}

	@Test
	@WithMockUser(
		username = "admin", password = "random", authorities= "ROLE_ADMIN"
	)
	@Order(2)
	void testDeletePlayerGames() throws Exception {
		List<GameDto> gamesDto = new ArrayList<GameDto>();
		when(gameService.deletePlayerGames(any(Long.class)))
			.thenReturn(gamesDto);
		this.mockMvc.perform(delete("/players/{id}/games", ID))
			.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.empty()))
			.andExpect(status().isOk());
		verify(gameService, times(1)).deletePlayerGames(any(Long.class));
	}

	@Test
	@Order(3)
	void testGetGamesByPlayerId() throws Exception {
		List<GameDto> gamesDto = new ArrayList<GameDto>();
		GameDto gameDto = new GameDto();
		gameDto.setId("Id1");
		gameDto.setVictory(true);
		gamesDto.add(gameDto);
		when(gameService.getGamesByPlayerId(any(Long.class))).thenReturn(gamesDto);
		this.mockMvc.perform(get("/players/{id}/games", ID))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("Id1"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].victory").value("true"));
		verify(gameService, times(1)).getGamesByPlayerId(1L);
	}

}
