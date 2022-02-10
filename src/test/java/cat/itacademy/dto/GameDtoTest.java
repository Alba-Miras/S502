package cat.itacademy.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@TestMethodOrder(OrderAnnotation.class)
class GameDtoTest {

	private static GameDto gameDto;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		gameDto = new GameDto();
	}

	@Test
	@Order(1)
	void testGetId() {
		gameDto.setId("Id1");
		assertEquals("Id1", gameDto.getId());
	}

	@Test
	@Order(2)
	void testGetPlayer() {;
		gameDto.setPlayerId(1L);
		assertEquals(1L, gameDto.getPlayerId());
	}

	@Test
	@Order(3)
	void testGetDice1() {
		gameDto.setDice1(5);
		assertEquals(5, gameDto.getDice1());
	}

	@Test
	@Order(4)
	void testGetDice2() {
		gameDto.setDice2(5);
		assertEquals(5, gameDto.getDice2());
	}

	@Test
	@Order(5)
	void testIsVictory() {
		gameDto.setVictory(true);
		assertTrue(gameDto.isVictory());
	}

}
