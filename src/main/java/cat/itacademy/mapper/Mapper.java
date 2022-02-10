package cat.itacademy.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cat.itacademy.dto.GameDto;
import cat.itacademy.dto.PlayerDto;
import cat.itacademy.entity.Game;
import cat.itacademy.entity.Player;
import cat.itacademy.repository.GameRepository;

@Component
public class Mapper {
	
	@Autowired
	private GameRepository gameRepository;
	
	public Mapper() {
		
	}
	
	public PlayerDto onePlayerToDto(Player player) {
		PlayerDto dto = new PlayerDto();
		dto.setId(player.getId());
		dto.setName(player.getName());
		//Convert Date to String
		dto.setRegistryDate(player.getRegistryDate().toString());
		List<GameDto> gamesDto = new ArrayList<GameDto>();
		//Get gamesDto list from player 
		gamesDto = gamesToDto(
			gameRepository.findAllByPlayerId(player.getId())
		);
		//Convert gamesDto list to Long list
		dto.setGamesId(this.getGamesId(gamesDto));
		//Calculate player success rate
		dto.setSuccessRate(this.getSuccessRate(gamesDto));
		return dto;
	}
	
	public List<PlayerDto> playersToDto(List<Player> players) {
		List<PlayerDto> playersDto = new ArrayList<PlayerDto>();
		players.stream()
			.forEach(player -> playersDto.add(this.onePlayerToDto(player)));
		return playersDto;
	}

	public GameDto oneGameToDto(Game game) {
		GameDto dto = new GameDto();
		dto.setId(game.getId());
		dto.setPlayerId(game.getPlayerId());
		dto.setDice1(game.getDice1());
		dto.setDice2(game.getDice2());
		dto.setVictory(game.getIsVictory());
		return dto;
	}
	
	public List<GameDto> gamesToDto(List<Game> games) {
		List<GameDto> gamesDto = new ArrayList<GameDto>();
		games.stream()
			.forEach(game -> gamesDto.add(this.oneGameToDto(game)));
		return gamesDto;
	}
	
	private List<String> getGamesId(List<GameDto> gamesDto) {
		List<String> gamesId = new ArrayList<String>();
		gamesDto.stream().forEach(gameDto -> gamesId.add(gameDto.getId()));
		return gamesId;
	}
	
	private double getSuccessRate(List<GameDto> gamesDto) {
		double victory = 0;
		for (GameDto gameDto : gamesDto) {
			if (gameDto.isVictory()) {
				victory = victory + 1;
			}
		}
		return (double) Math.round(
			100 * (100 * victory / gamesDto.size())
		) / 100;
	}
	
}
