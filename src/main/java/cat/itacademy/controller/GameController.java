package cat.itacademy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.dto.GameDto;
import cat.itacademy.service.GameService;

@RestController
@RequestMapping("/players/{playerId}/games")
public class GameController {
	
	@Autowired
	private GameService gameService;
	
	//Add new game to player
	@PostMapping
	public ResponseEntity<GameDto> play(
		@PathVariable(name="playerId") Long playerId
	) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(gameService.play(playerId));
	}
	
	//Delete all games from player
	@DeleteMapping
	public ResponseEntity<List<GameDto>> deletePlayerGames(
		@PathVariable(name="playerId") Long playerId
	) {
		return ResponseEntity.ok()
			.body(gameService.deletePlayerGames(playerId));
	}
	
	//List all games from player
	@GetMapping
	public ResponseEntity<List<GameDto>> getGamesByPlayerId(
		@PathVariable(name="playerId") Long playerId
	) {
		return ResponseEntity.ok()
			.body(gameService.getGamesByPlayerId(playerId));
	}	

}
