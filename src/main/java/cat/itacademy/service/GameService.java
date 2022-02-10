package cat.itacademy.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.dto.GameDto;
import cat.itacademy.entity.Game;
import cat.itacademy.mapper.Mapper;
import cat.itacademy.repository.GameRepository;
import cat.itacademy.repository.PlayerRepository;

@Service
public class GameService {
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private Mapper mapper;
	
	//Insert new game into data base and return it
	public GameDto play(Long playerId) {
		this.checkPlayerExists(playerId);
		return mapper.oneGameToDto(gameRepository.insert(new Game(playerId)));
	}

	//Delete all games from one player and return them
	public List<GameDto> deletePlayerGames(Long playerId) {
		this.checkPlayerExists(playerId);
		return mapper.gamesToDto(gameRepository.deleteAllByPlayerId(playerId));
	}
	
	//Get all games from one player
	public List<GameDto> getGamesByPlayerId(Long playerId) {
		this.checkPlayerExists(playerId);
		return mapper.gamesToDto(gameRepository.findAllByPlayerId(playerId));
	}
	
	//Check player from request exists
	private void checkPlayerExists(Long playerId) {
		if (!playerRepository.existsById(playerId)) {
			throw new EntityNotFoundException("Player not found.");
		}
	}

}
