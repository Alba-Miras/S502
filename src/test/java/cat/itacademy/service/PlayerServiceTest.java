package cat.itacademy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import cat.itacademy.dto.PlayerDto;
import cat.itacademy.entity.Player;
import cat.itacademy.mapper.Mapper;
import cat.itacademy.repository.PlayerRepository;

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
class PlayerServiceTest {
	
	@Mock
	private PlayerRepository playerRepository;
	
	@Mock
	private Mapper mapper;
	
	@InjectMocks
	private PlayerService playerService;
	
	private final Long ID = 1L;
	private PlayerDto playerDto;

	@BeforeEach
	void setUp() throws Exception {
		this.playerDto = new PlayerDto();
	}
	
	private static Stream<Arguments> getNames() {
		return Stream.of(
			Arguments.of("anònim", "Anònim"),
			Arguments.of("anonim", "Anònim"),
			Arguments.of("Valid", "Valid"));
	}
			
	@ParameterizedTest
	@MethodSource("getNames")
	@Order(1)
	void testAddPlayer(String name, String expectedName)
		throws EntityExistsException {
		playerDto.setName(name);
		PlayerDto expectedDto = new PlayerDto();
		expectedDto.setName(expectedName);
		when(playerRepository.existsByName(any(String.class))).thenReturn(false);
		when(playerRepository.save(any(Player.class)))
			.thenAnswer(i -> i.getArgument(0));
		when(mapper.onePlayerToDto(any(Player.class))).thenReturn(expectedDto);

		assertEquals(expectedName, playerService.addPlayer(playerDto).getName());
		
		ArgumentCaptor<Player> argument = ArgumentCaptor.forClass(Player.class);
		verify(playerRepository, times(1)).save(any(Player.class));
		verify(mapper, times(1)).onePlayerToDto(argument.capture());
		assertEquals(expectedName, argument.getValue().getName());
	}
	
	@Test
	@Order(2)
	void testAddPlayerThrowsInputMismatchExceptionWhenInvalidPlayerFormat()
		throws InputMismatchException {
		playerDto.setName(null);
		assertThrows(
			InputMismatchException.class,
			() -> this.playerService.addPlayer(playerDto)
		);
		playerDto.setName(" ");
		assertThrows(
			InputMismatchException.class,
			() -> this.playerService.addPlayer(playerDto)
		);
	}

	@Test
	@Order(3)
	void testAddPlayerThrowsPlayerAlreadyExistsExceptionWhenPlayerAlreadyExists()
		throws EntityExistsException {
		playerDto.setName("Not valid");
		when(playerRepository.existsByName(any(String.class))).thenReturn(true); 
		assertThrows(
			EntityExistsException.class,
			() -> this.playerService.addPlayer(playerDto)
		);
		verify(playerRepository, times(1)).existsByName(any(String.class));
	}

	@Test
	@Order(4)
	void testUpdatePlayerName() throws EntityExistsException {
		Player player = new Player("Player 1");
		player.setId(ID);
		playerDto.setId(ID);
		playerDto.setName("New name");
		when(playerRepository.existsByName(any(String.class))).thenReturn(false);
		when(playerRepository.findById(any(Long.class)))
			.thenReturn(Optional.of(player));
		when(playerRepository.save(any(Player.class)))
			.thenAnswer(i -> i.getArgument(0));
		when(mapper.onePlayerToDto(any(Player.class))).thenReturn(playerDto);
		
		assertEquals(
			"New name",
			playerService.updatePlayerName(ID, playerDto).getName()
		);
		verify(playerRepository, times(1)).existsByName(any(String.class));
		verify(playerRepository, times(1)).findById(any(Long.class));
		
		ArgumentCaptor<Player> argument = ArgumentCaptor.forClass(Player.class);
		verify(playerRepository, times(1)).save(argument.capture());
		assertEquals("New name", argument.getValue().getName());
		verify(mapper, times(1)).onePlayerToDto(any(Player.class));
	}
	
	@Test
	@Order(5)
	void testUpdatePlayerNameThrowsInputMismatchExceptionWhenInvalidNameFormat()
		throws InputMismatchException {
		playerDto.setId(ID);
		playerDto.setName(null);
		assertThrows(
			InputMismatchException.class,
			() -> this.playerService.updatePlayerName(ID, playerDto)
		);
		playerDto.setName(" ");
		assertThrows(
			InputMismatchException.class,
			() -> this.playerService.updatePlayerName(ID, playerDto)
		);
	}
	
	@Test
	@Order(6)
	void testUpdatePlayerNameEntityNotFoundExceptionWhenPlayerDoesNotExist() {
		when(playerRepository.findById(any(Long.class))).thenReturn(Optional.empty());
		
		playerDto.setId(1L);
		playerDto.setName("Player 1");
		assertThrows(
			EntityNotFoundException.class,
			() -> this.playerService.updatePlayerName(1L, playerDto)
		);
		
		playerDto.setId(null);
		assertThrows(
			EntityNotFoundException.class,
			() -> this.playerService.updatePlayerName(1L, playerDto)
		);
		
		verify(playerRepository, times(2)).findById(any(Long.class));
	}
	
	@Test
	@Order(7)
	void testUpdatePlayerNameInputMismatchExceptionWhenInvalidPlayerId() {
		playerDto.setId(3L);
		assertThrows(
			InputMismatchException.class,
			() -> this.playerService.updatePlayerName(ID, playerDto)
		);
	}
	
	@Test
	@Order(8)
	void testUpdatePlayerNameThrowsPlayerAlreadyExistsExceptionWhenPlayerAlreadyExists() {
		playerDto.setId(ID);
		playerDto.setName("Name in use");
		when(playerRepository.existsByName(any(String.class))).thenReturn(true);
		assertThrows(
			EntityExistsException.class,
			() -> this.playerService.updatePlayerName(ID, playerDto)
		);
		verify(playerRepository, times(1)).existsByName(any(String.class));
	}
	
	@Test
	@Order(9)
	void testGetPlayersAndAverageScore() {
		List<PlayerDto> playersDto = new ArrayList<PlayerDto>();
		PlayerDto playerDto1 = new PlayerDto();
		PlayerDto playerDto2 = new PlayerDto();
		playerDto1.setName("Player 1");
		playerDto2.setName("Player 2");
		playerDto1.setSuccessRate(0.0);
		playerDto2.setSuccessRate(100.0);
		playersDto.add(playerDto1);
		playersDto.add(playerDto2);
		
		when(playerRepository.findAll()).thenReturn(new ArrayList<Player>());
		when(mapper.playersToDto(anyList())).thenReturn(playersDto);
		
		assertEquals(
			"Players:\n"
			+ "Name: Player 1, success rate: 0.0%\n"
			+ "Name: Player 2, success rate: 100.0%\n",
			playerService.getPlayersAndAverageScore()
		);
		verify(playerRepository, times(1)).findAll();
	}
	
}