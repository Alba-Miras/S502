package cat.itacademy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.dto.UserDto;
import cat.itacademy.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//Add new user with user role
	@PostMapping
	public ResponseEntity<String> addUser(
		@RequestBody UserDto userDto
	) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(userService.addUser(userDto));
	}
	
}
