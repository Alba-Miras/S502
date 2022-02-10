package cat.itacademy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.dto.PlayerDto;
import cat.itacademy.service.RankingService;

@RestController
@RequestMapping("/players/ranking")
public class RankingController {
	
	@Autowired
	private RankingService rankingService;

	//Get average score from all players
	@GetMapping
	public ResponseEntity<Double> getAverageSuccessRate() {
		return ResponseEntity.ok()
			.body(rankingService.getOverallAverageScore());
	}
		
	//List all players with lowest success rate
	@GetMapping("/losers")
	public ResponseEntity<List<PlayerDto>> getLosers() {
		return ResponseEntity.ok().body(rankingService.getLosers());
	}
	
	//List all players with highest success rate
	@GetMapping("/winners")
	public ResponseEntity<List<PlayerDto>> getWinners() {
		return ResponseEntity.ok().body(rankingService.getWinners());
	}
	
}
