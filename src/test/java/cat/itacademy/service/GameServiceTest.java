package cat.itacademy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import cat.itacademy.dto.GameDto;
import cat.itacademy.entity.Game;
import cat.itacademy.mapper.Mapper;
import cat.itacademy.repository.GameRepository;
import cat.itacademy.repository.PlayerRepository;

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
class GameServiceTest {

	@Mock
	private PlayerRepository playerRepository;
	
	@Mock
	private GameRepository gameRepository;
	
	@Mock
	private Mapper mapper;
	
	@InjectMocks
	private GameService gameService;
	
	private final Long ID = 1L;
	
	@Test
	@Order(1)
	void testPlay() {
		GameDto GameDto = new GameDto();
		when(playerRepository.existsById(any(Long.class))).thenReturn(true);
		when(gameRepository.insert(any(Game.class)))
			.then(i -> {
				return i.getArgument(0);
			});
		when(mapper.oneGameToDto(any(Game.class))).thenReturn(GameDto);
		
		assertEquals(GameDto, gameService.play(ID));
		
		verify(playerRepository, times(1)).existsById(any(Long.class));
		verify(gameRepository, times(1)).insert(any(Game.class));
		ArgumentCaptor<Game> argument = ArgumentCaptor.forClass(Game.class);
		verify(mapper, times(1)).oneGameToDto(argument.capture());
		assertEquals(1L, argument.getValue().getPlayerId());
		assertNotNull(argument.getValue().getDice1());
		assertNotNull(argument.getValue().getDice2());
	}
	
	@Test
	@Order(2)
	void testPlayThrowsEntityNotFoundExceptionWhenPlayerDoesNotExist() {
		when(playerRepository.existsById(any(Long.class))).thenReturn(false);
		assertThrows(EntityNotFoundException.class, () -> this.gameService.play(ID));
		verify(playerRepository, times(1)).existsById(any(Long.class));
	}
	
	@Test
	@Order(3)
	void testDeletePlayerGames() {
		List<Game> games1 = new ArrayList<Game>();
		Game game = new Game();
		List<GameDto> gamesDto = new ArrayList<GameDto>();
		GameDto gameDto = new GameDto();
		when(playerRepository.existsById(any(Long.class))).thenReturn(true);
		when(gameRepository.deleteAllByPlayerId(any(Long.class)))
			.then(i -> { 
				game.setPlayerId(i.getArgument(0));
				games1.add(game);
				return games1;
			});
		when(mapper.gamesToDto(anyList()))
		.then(i -> { 
			List<Game> games2 = new ArrayList<Game>();
			games2 = i.getArgument(0);
			gameDto.setPlayerId(games2.get(0).getPlayerId());
			gamesDto.add(gameDto);
			return gamesDto;
		});
		
		assertEquals(ID, gameService.deletePlayerGames(ID).get(0).getPlayerId());
		verify(playerRepository, times(1)).existsById(any(Long.class));
		verify(gameRepository, times(1)).deleteAllByPlayerId(any(Long.class));
		verify(mapper, times(1)).gamesToDto(anyList());
	}
	
	@Test
	@Order(4)
	void testDeletePlayerGamesThrowsEntityNotFoundExceptionWhenPlayerDoesNotExist() {
		when(playerRepository.existsById(any(Long.class))).thenReturn(false);
		assertThrows(
			EntityNotFoundException.class,
			() -> this.gameService.deletePlayerGames(1L)
		);
		verify(playerRepository, times(1)).existsById(any(Long.class));
	}

	@Test
	@Order(5)
	void testGetGamesByPlayerId() {
		List<Game> games1 = new ArrayList<Game>();
		Game game = new Game();
		List<GameDto> gamesDto = new ArrayList<GameDto>();
		GameDto gameDto = new GameDto();
		when(playerRepository.existsById(any(Long.class))).thenReturn(true);
		when(gameRepository.findAllByPlayerId(any(Long.class)))
			.then(i -> { 
				game.setPlayerId(i.getArgument(0));
				games1.add(game);
				return games1;
			});
		when(mapper.gamesToDto(anyList()))
		.then(i -> { 
			List<Game> games2 = new ArrayList<Game>();
			games2 = i.getArgument(0);
			gameDto.setPlayerId(games2.get(0).getPlayerId());
			gamesDto.add(gameDto);
			return gamesDto;
		});
		 
		assertEquals(ID, gameService.getGamesByPlayerId(ID).get(0).getPlayerId());
		verify(playerRepository, times(1)).existsById(any(Long.class));
		verify(gameRepository, times(1)).findAllByPlayerId(ID);
		verify(mapper, times(1)).gamesToDto(anyList());
	}
	
	@Test
	@Order(6)
	void testGetGamesByPlayerIdThrowsEntityNotFoundExceptionWhenPlayerDoesNotExist() {
		when(playerRepository.existsById(any(Long.class))).thenReturn(false);
		assertThrows(
			EntityNotFoundException.class, 
			() -> this.gameService.getGamesByPlayerId(ID)
		);
		verify(playerRepository, times(1)).existsById(any(Long.class));
	}

}
