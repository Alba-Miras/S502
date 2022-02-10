package cat.itacademy.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class PlayerTest {

	private static Player player;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		player = new Player();
	}

	@Test
	@Order(1)
	void testGetId() {
		player.setId(1L);
		assertEquals(1L, player.getId());
	}

	@Test
	@Order(2)
	void testGetName() {
		player.setName("Name2");
		assertEquals("Name2", player.getName());
	}

	@Test
	@Order(3)
	void testGetRegistryDate() {
		Date date = new Date();
		player.setRegistryDate(date);
		assertEquals(date, player.getRegistryDate());
	}

}
