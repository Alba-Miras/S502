package cat.itacademy.exception;

import java.util.InputMismatchException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptions {

	@ExceptionHandler(value = {
		EntityNotFoundException.class,
		UsernameNotFoundException.class
	})
	public ResponseEntity<ErrorResponse> handleEntityNotFoundException(
		Exception ex
	) {
		return new ResponseEntity<ErrorResponse>(
			new ErrorResponse(HttpStatus.NOT_FOUND.value(),	ex.getMessage()),
			HttpStatus.NOT_FOUND
		);
	}
	
	@ExceptionHandler(value = {
		EntityExistsException.class,
		InputMismatchException.class,
	})
	public ResponseEntity<ErrorResponse> handleBadRequestException(
		Exception ex
	) {	
		return new ResponseEntity<ErrorResponse>(
			new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
				ex.getMessage()),
			HttpStatus.BAD_REQUEST
		);
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex) {
		return new ResponseEntity<ErrorResponse>(
			new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				ex.getMessage()),
			HttpStatus.INTERNAL_SERVER_ERROR
		);
	}

}
