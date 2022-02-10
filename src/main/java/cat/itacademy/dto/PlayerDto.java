package cat.itacademy.dto;

import java.util.List;

public class PlayerDto {
	
	private Long id;
	private String name;
	private String registryDate;
	private List<String> gamesId;
	private double successRate;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegistryDate() {
		return registryDate;
	}

	public void setRegistryDate(String registryDate) {
		this.registryDate = registryDate;
	}

	public List<String> getGamesId() {
		return gamesId;
	}

	public void setGamesId(List<String> list) {
		this.gamesId = list;
	}

	public double getSuccessRate() {
		return successRate;
	}

	public void setSuccessRate(double successRate) {
		this.successRate = successRate;
	}
	
}
