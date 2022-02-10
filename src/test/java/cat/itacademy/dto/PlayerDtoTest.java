package cat.itacademy.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@TestMethodOrder(OrderAnnotation.class)
class PlayerDtoTest {

	private static PlayerDto playerDto;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		playerDto = new PlayerDto();
	}

	@Test
	@Order(1)
	void testGetId() {
		playerDto.setId(1L);
		assertEquals(1L, playerDto.getId());
	}

	@Test
	@Order(2)
	void testGetName() {
		playerDto.setName("Name");
		assertEquals("Name", playerDto.getName());
	}

	@Test
	@Order(3)
	void testGetRegistryDate() {
		String date = new Date().toString();
		playerDto.setRegistryDate(date);
		assertEquals(date, playerDto.getRegistryDate());
	}
	
	@Test
	@Order(4)
	void testGetGamesId() {
		List<String> gamesId = new ArrayList<String>();
		playerDto.setGamesId(gamesId);
		assertEquals(gamesId, playerDto.getGamesId());
	}
	
	@Test
	@Order(5)
	void testGetSuccessRate() {
		playerDto.setSuccessRate(50.0);
		assertEquals(50.0, playerDto.getSuccessRate());
	}
	
}
