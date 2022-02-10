package cat.itacademy.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class RoleTest {
	
	private static Role role;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		role = new Role();
	}

	@Test
	@Order(1)
	void testGetId() {
		role.setId(1L);
		assertEquals(1L, role.getId());
	}

	@Test
	@Order(2)
	void testGetRoleName() {
		role.setRoleName(RoleEnum.ROLE_USER);
		assertEquals(RoleEnum.ROLE_USER, role.getRoleName());
	}

}
