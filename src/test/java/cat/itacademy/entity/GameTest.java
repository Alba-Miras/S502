package cat.itacademy.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class GameTest {

	private static Game game;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		game = new Game();
	}

	@Test
	@Order(1)
	void testGetId() {
		game.setId("Id1");
		assertEquals("Id1", game.getId());
	}

	@Test
	@Order(2)
	void testGetPlayerId() {
		game.setPlayerId(1L);
		assertEquals(1L, game.getPlayerId());
	}

	@Test
	@Order(3)
	void testGetDice1() {
		game.setDice1(5);
		assertEquals(5, game.getDice1());
	}

	@Test
	@Order(4)
	void testGetDice2() {
		game.setDice2(5);
		assertEquals(5, game.getDice2());
	}

	@Test
	@Order(5)
	void testGetIsVictory() {
		assertFalse(game.getIsVictory());
		game.setDice1(5);
		game.setDice2(2);
		assertTrue(game.getIsVictory());
		
	}
	
}
