package cat.itacademy.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import cat.itacademy.dto.PlayerDto;
import cat.itacademy.service.RankingService;

@TestMethodOrder(OrderAnnotation.class)
@WebMvcTest(controllers = RankingController.class)
class RankingControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserDetailsService userDetailsService;
	
	@MockBean
	private BCryptPasswordEncoder encoder;

	@MockBean
	private RankingService rankingService;
	
	@Test
	@Order(1)
	void testGetAverageSuccessRate() throws Exception {
		when(rankingService.getOverallAverageScore()).thenReturn(50.0);
		this.mockMvc.perform(get("/players/ranking"))
			.andExpect(MockMvcResultMatchers.jsonPath("$").value(50.0))
			.andExpect(status().isOk());;
		verify(rankingService, times(1)).getOverallAverageScore();
	}

	@Test
	@Order(2)
	void testGetLosers() throws Exception {
		PlayerDto playerDto = new PlayerDto();;
		playerDto.setSuccessRate(0);
		List<PlayerDto> playersDto = new ArrayList<PlayerDto>();
		playersDto.add(playerDto);
		when(rankingService.getLosers()).thenReturn(playersDto);
		this.mockMvc.perform(get("/players/ranking/losers"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].successRate").value(0))
			.andExpect(status().isOk());;
		verify(rankingService, times(1)).getLosers();
	}

	@Test
	@Order(3)
	void testGetWinners() throws Exception {
		PlayerDto playerDto = new PlayerDto();;
		playerDto.setSuccessRate(100);
		List<PlayerDto> playersDto = new ArrayList<PlayerDto>();
		playersDto.add(playerDto);
		when(rankingService.getWinners()).thenReturn(playersDto);
		this.mockMvc.perform(get("/players/ranking/winners"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].successRate").value(100))
			.andExpect(status().isOk());
		verify(rankingService, times(1)).getWinners();
	}

}
