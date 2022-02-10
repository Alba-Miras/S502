package cat.itacademy.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserDtoTest {
	
	private static UserDto userDto;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		userDto = new UserDto();
	}

	@Test
	void testGetUserName() {
		userDto.setUserName("Name");
		assertEquals("Name", userDto.getUserName());
	}

	@Test
	void testGetPassword() {
		userDto.setPassword("password");
		assertEquals("password", userDto.getPassword());
	}

}
