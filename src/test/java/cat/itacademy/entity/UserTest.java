package cat.itacademy.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class UserTest {
	
	private static User user;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		user = new User();
	}

	@Test
	@Order(1)
	void testAddRole() {
		Role role = new Role();
		user.setRoles(new ArrayList<Role>());
		user.addRole(role);
		assertEquals(role, user.getRoles().get(0));
	}

	@Test
	@Order(2)
	void testGetId() {
		user.setId(1L);
		assertEquals(1L, user.getId());
	}

	@Test
	@Order(3)
	void testGetUserName() {
		user.setUserName("User");
		assertEquals("User", user.getUserName());
	}

	@Test
	@Order(4)
	void testGetPassword() {
		user.setPassword("password");;
		assertEquals("password", user.getPassword());
	}

	@Test
	@Order(5)
	void testGetRoles() {
		List<Role> roles = new ArrayList<Role>();
		user.setRoles(roles);
		assertEquals(roles, user.getRoles());
	}

}
