package cat.itacademy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.dto.PlayerDto;
import cat.itacademy.service.PlayerService;

@RestController
@RequestMapping("/players")
public class PlayerController {
	
	@Autowired
	private PlayerService playerService;
	
	//Add new player, player name required
	@PostMapping
	public ResponseEntity<PlayerDto> addPlayer(
		@RequestBody PlayerDto playerDto
	) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(playerService.addPlayer(playerDto));
	}
	
	//Update player name, new name required
	@PutMapping({"/{playerId}"})
	public ResponseEntity<PlayerDto> updatePlayerName(
		@RequestBody PlayerDto playerDto,
		@PathVariable(name="playerId") Long playerId
	) {
		return ResponseEntity.ok()
			.body(playerService.updatePlayerName(playerId, playerDto));
	}
	
	//Print all players names and their success rate
	@GetMapping
	public ResponseEntity<String> getPlayersNameAndSuccessRate() {
		return ResponseEntity.ok()
			.body(playerService.getPlayersAndAverageScore());
	}
	
}
