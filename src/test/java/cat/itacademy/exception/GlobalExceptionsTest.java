package cat.itacademy.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class GlobalExceptionsTest {
	
	private static GlobalExceptions exception;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		exception = new GlobalExceptions();
	}

	@Test
	@Order(1)
	void testHandleEntityNotFoundException() {
		EntityNotFoundException ex =
			new EntityNotFoundException("Entity not found.");
		assertEquals(
			404,
			exception.handleEntityNotFoundException(ex).getBody().getStatusCode()
		);
		assertEquals(
			"Entity not found.",
			exception.handleEntityNotFoundException(ex).getBody().getMessage()
		);
		assertEquals(
			404,
			exception.handleEntityNotFoundException(ex).getStatusCodeValue()
		);
	}

	@Test
	@Order(2)
	void testHandleBadRequestException() {
		EntityExistsException ex =
			new EntityExistsException("Entity already exists.");
		assertEquals(
			400,
			exception.handleBadRequestException(ex).getBody().getStatusCode()
		);
		assertEquals(
			"Entity already exists.",
			exception.handleBadRequestException(ex).getBody().getMessage()
		);
		assertEquals(
			400,
			exception.handleBadRequestException(ex).getStatusCodeValue()
		);
	}

	@Test
	@Order(3)
	void testHandleException() {
		Exception ex = new Exception("");
		assertEquals(
			500,
			exception.handleException(ex).getBody().getStatusCode()
		);
		assertEquals(
			"",
			exception.handleException(ex).getBody().getMessage()
		);
		assertEquals(
			500,
			exception.handleException(ex).getStatusCodeValue()
		);
	}
	
}
