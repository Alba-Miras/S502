package cat.itacademy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.dto.PlayerDto;
import cat.itacademy.mapper.Mapper;
import cat.itacademy.repository.PlayerRepository;

@Service
public class RankingService {
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private Mapper mapper;

	//Calculate and return players average success rate
	public Double getOverallAverageScore() {
		List<PlayerDto> playersDto = mapper.playersToDto(playerRepository.findAll());
		double average = 0;
		for (PlayerDto dto : playersDto) {
			average = average + dto.getSuccessRate();
		}
		return (double) Math.round(100 * (average / playersDto.size())) / 100;
	}
	
	//Calculate and return players with lowest success rate
	public List<PlayerDto> getLosers() {
		List<PlayerDto> playersDto = mapper.playersToDto(playerRepository.findAll());
		double min = 100.0;
		List<PlayerDto> losers = new ArrayList<PlayerDto>();
		for (PlayerDto dto : playersDto) {
			if (dto.getSuccessRate() <= min) {
				if (dto.getSuccessRate() < min) {
				min = dto.getSuccessRate();
				losers.clear();
				}
				if (dto.getSuccessRate() == min) {
				losers.add(dto);
				}
			}
		}
		return losers; 
	}	
	
	//Calculate and return players with highest success rate
	public List<PlayerDto> getWinners() {
		List<PlayerDto> playersDto = mapper.playersToDto(playerRepository.findAll());
		double max = 0.0;
		List<PlayerDto> winners = new ArrayList<PlayerDto>();
		for (PlayerDto dto : playersDto) {
			if (dto.getSuccessRate() >= max) {
				if (dto.getSuccessRate() > max) {
				max = dto.getSuccessRate();
				winners.clear();
				}
				if (dto.getSuccessRate() == max) {
				winners.add(dto);
				}
			}
		}
		return winners; 
	}
	
}
