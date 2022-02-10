package cat.itacademy.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@TestMethodOrder(OrderAnnotation.class)
class ErrorResponseTest {

	@Test
	@Order(1)
	void testGetStatusCode() {
		ErrorResponse error = new ErrorResponse(0, "");
		error.setStatusCode(400);
		assertEquals(400, error.getStatusCode());
	}

	@Test
	@Order(2)
	void testGetMessage() {
		ErrorResponse error = new ErrorResponse(0, "");
		error.setMessage("Bad request");
		assertEquals("Bad request", error.getMessage());
	}

}
