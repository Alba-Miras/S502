package cat.itacademy.service;

import java.util.InputMismatchException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.dto.PlayerDto;
import cat.itacademy.entity.Player;
import cat.itacademy.mapper.Mapper;
import cat.itacademy.repository.PlayerRepository;

@Service
public class PlayerService {
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private Mapper mapper;
	
	//Add new player with valid name
	public PlayerDto addPlayer(PlayerDto playerDto) {
		this.validatePlayerNameFormat(playerDto.getName());
		Player player = new Player(
			this.getValidPlayerName(playerDto.getName())
		);
		return mapper.onePlayerToDto(playerRepository.save(player));
	}
	
	//Update player name if parameters are valid
	public PlayerDto updatePlayerName(Long playerId, PlayerDto playerDto) {
		this.validatePlayerId(playerId, playerDto.getId());
		this.validatePlayerNameFormat(playerDto.getName());
		String name = this.getValidPlayerName(playerDto.getName());
		Player player = playerRepository
				.findById(playerId)
				.orElseThrow(
					() -> new EntityNotFoundException("Player not found.")
				);
		player.setName(name);
		return mapper.onePlayerToDto(playerRepository.save(player));
	}
	
	//Get all players info and return their names and success rates in String format
	public String getPlayersAndAverageScore() {
		String players = "Players:\n";
		for (PlayerDto dto : mapper.playersToDto(playerRepository.findAll())) {
			players = players + "Name: " + dto.getName() 
			+ ", success rate: " + dto.getSuccessRate() + "%\n";
		}
		return players;
	}
	
	private void validatePlayerNameFormat(String name) {	
		if (name == null || name.isBlank()) {
			throw new InputMismatchException(
				"Player name cannot be empty."
			);
		};
	}
	
	private void validatePlayerId(Long urlId, Long playerDtoId) {
		if (playerDtoId != null && playerDtoId != urlId) {
			throw new InputMismatchException(
				"URL id and body id do not match."
			);
		};
	}
	
	//Standardize anonymous name, check name is not taken, return valid name
	private String getValidPlayerName(String name) {
		if (name.equalsIgnoreCase("anonim")
			|| name.equalsIgnoreCase("anònim")) {
			return "Anònim";
		} else if (playerRepository.existsByName(name)) {
			throw new EntityExistsException(
				"Player with the same name already exists."
			);
		} else {
			return name;
		}
	}
	
}
