package cat.itacademy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import cat.itacademy.dto.PlayerDto;
import cat.itacademy.entity.Player;
import cat.itacademy.mapper.Mapper;
import cat.itacademy.repository.PlayerRepository;

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
class RankingServiceTest {
	
	@Mock
	private PlayerRepository playerRepository;
	
	@Mock
	private Mapper mapper;
	
	@InjectMocks
	private RankingService rankingService;
	
	private static List<Player> players;
	private static List<PlayerDto> playersDto;
	private static PlayerDto playerDto1;
	private static PlayerDto playerDto2;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		players = new ArrayList<Player>();
	
		playerDto1 = new PlayerDto();
		playerDto2 = new PlayerDto();
		playerDto1.setName("Player 1");
		playerDto2.setName("Player 2");
		playerDto1.setSuccessRate(0);
		playerDto2.setSuccessRate(100);
		
		playersDto = new ArrayList<PlayerDto>();
		playersDto.add(playerDto1);
		playersDto.add(playerDto2);
	}

	@Test
	@Order(1)
	void testGetOverallAverageScore() {
		when(playerRepository.findAll()).thenReturn(players);
		when(mapper.playersToDto(anyList())).thenReturn(playersDto);
		
		assertEquals(50, rankingService.getOverallAverageScore());
		verify(playerRepository, times(1)).findAll();
		verify(mapper, times(1)).playersToDto(anyList());
	}
	
	@Test
	@Order(2)
	void testGetLosers() {
		when(playerRepository.findAll()).thenReturn(players);
		when(mapper.playersToDto((anyList()))).thenReturn(playersDto);
		
		assertEquals("Player 1", rankingService.getLosers().get(0).getName());
		verify(playerRepository, times(1)).findAll();
		verify(mapper, times(1)).playersToDto(anyList());
	}

	@Test
	@Order(3)
	void testGetWinners() {
		when(playerRepository.findAll()).thenReturn(players);
		when(mapper.playersToDto((anyList()))).thenReturn(playersDto);
		
		assertEquals("Player 2", rankingService.getWinners().get(0).getName());
		verify(playerRepository, times(1)).findAll();
		verify(mapper, times(1)).playersToDto(anyList());
	}

}
