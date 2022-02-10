package cat.itacademy.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
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

import cat.itacademy.dto.GameDto;
import cat.itacademy.dto.PlayerDto;
import cat.itacademy.entity.Game;
import cat.itacademy.entity.Player;
import cat.itacademy.repository.GameRepository;

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
class MapperTest {
		
	@Mock
	private GameRepository gameRepository;
		
	@InjectMocks
	private Mapper mapper;

	private static Player player1;
	private static Player player2;
	private static List<Player> players;
	private static Game game1;
	private static Game game2;
	private static GameDto gameDto1;
	private static GameDto gameDto2;
	private static List<Game> games1;
	private static List<Game> games2;
	private static PlayerDto playerDto1;
	private static PlayerDto playerDto2;
	private static List<String> gamesId1;
	private static List<String> gamesId2;
	private static List<PlayerDto> playersDto;
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		player1 = new Player();
		player2 = new Player();
		player1.setId(1L);
		player2.setId(2L);
		player1.setName("Player 1");
		player2.setName("Player 2");
		Date date1 = new Date();
		Date date2 = new Date();
		player1.setRegistryDate(date1);
		player2.setRegistryDate(date2);
		
		players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);
		
		game1 = new Game();
		game1.setId("Id1");
		game1.setPlayerId(1L);
		game1.setDice1(3);
		game1.setDice2(5);
		game2 = new Game();
		game2.setId("Id2");
		game2.setPlayerId(1L);
		game2.setDice1(1);
		game2.setDice2(6);
		
		gameDto1 = new GameDto();
		gameDto1.setId("Id1");
		gameDto1.setPlayerId(1L);
		gameDto1.setVictory(false);
		gameDto2 = new GameDto();
		gameDto2.setId("Id2");
		gameDto2.setPlayerId(1L);
		gameDto2.setVictory(true);

		games1 = new ArrayList<Game>();
		games1.add(game1);
		games2 = new ArrayList<Game>();
		games2.add(game2);
		
		playerDto1 = new PlayerDto();
		playerDto2 = new PlayerDto();
		playerDto1.setId(1L);
		playerDto2.setId(2L);
		playerDto1.setName("Player 1");
		playerDto2.setName("Player 2");
		playerDto1.setRegistryDate(date1.toString());
		playerDto2.setRegistryDate(date2.toString());
		playerDto1.setSuccessRate(0.0);
		playerDto2.setSuccessRate(100.0);
		
		gamesId1 = new ArrayList<String>();
		gamesId1.add("Id1");
		playerDto1.setGamesId(gamesId1);
		gamesId2 = new ArrayList<String>();
		gamesId2.add("Id2");
		playerDto2.setGamesId(gamesId2);
		
		playersDto = new ArrayList<PlayerDto>();
		playersDto.add(playerDto1);
		playersDto.add(playerDto2);
	}

	@Test
	@Order(1)
	void testOnePlayerToDto() {
		when(gameRepository.findAllByPlayerId(any(Long.class)))
			.thenReturn(games1).thenReturn(games2);
		this.comparePlayersDto(playerDto1, mapper.onePlayerToDto(player1));
		this.comparePlayersDto(playerDto2, mapper.onePlayerToDto(player2));
	}

	@Test
	@Order(2)
	void testPlayersToDto() {
		when(gameRepository.findAllByPlayerId(any(Long.class)))
			.thenReturn(games1).thenReturn(games2);
		List<PlayerDto> newPlayersDto = mapper.playersToDto(players);
		this.comparePlayersDto(playerDto1, newPlayersDto.get(0));
		this.comparePlayersDto(playerDto2, newPlayersDto.get(1));
	}

	@Test
	@Order(3)
	void testOneGameToDto() {
		this.compareGamesDto(gameDto1, mapper.oneGameToDto(game1));
		this.compareGamesDto(gameDto2, mapper.oneGameToDto(game2));
	}

	@Test
	@Order(4)
	void testGamesToDto() {
		List<GameDto> newGamesDto1 = mapper.gamesToDto(games1);
		List<GameDto> newGamesDto2 = mapper.gamesToDto(games2);
		this.compareGamesDto(gameDto1, newGamesDto1.get(0));
		this.compareGamesDto(gameDto2, newGamesDto2.get(0));
	}
	
	private void comparePlayersDto(PlayerDto dto1, PlayerDto dto2) {
		assertEquals(dto1.getId(), dto2.getId());
		assertEquals(dto1.getName(), dto2.getName());
		assertEquals(dto1.getRegistryDate(), dto2.getRegistryDate());
		assertEquals(dto1.getGamesId(), dto2.getGamesId());
		assertEquals(dto1.getSuccessRate(), dto2.getSuccessRate());
	}
	
	private void compareGamesDto(GameDto dto1, GameDto dto2) {
		assertEquals(dto1.getId(), dto2.getId());
		assertEquals(dto1.isVictory(), dto2.isVictory());
		assertEquals(dto1.getPlayerId(), dto2.getPlayerId());
	}
	
}
