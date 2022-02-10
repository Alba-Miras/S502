package cat.itacademy.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.mongodb.lang.NonNull;

@Document(collection = "game")
public class Game {
	
	@Id
	@NonNull
	private String id;
	@Field (name = "playerId")
	@NonNull
	private Long playerId;
	@Field (name = "dice1")
	@NonNull
	private int dice1;
	@Field (name = "dice2")
	@NonNull
	private int dice2;
	
	@Transient
	private final int DICE_SIZE = 6;
	@Transient
	private final int WINNING_NUMBER = 7;
	
	public Game() {		
	}
	
	public Game(Long playerId) {
		this.dice1 = (int)(Math.random() * DICE_SIZE) + 1;
		this.dice2 = (int)(Math.random() * DICE_SIZE) + 1;
		this.playerId = playerId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	public int getDice1() {
		return dice1;
	}

	public void setDice1(int dice1) {
		this.dice1 = dice1;
	}

	public int getDice2() {
		return dice2;
	}

	public void setDice2(int dice2) {
		this.dice2 = dice2;
	}

	public boolean getIsVictory() {
		return dice1 + dice2 == WINNING_NUMBER;
	}

}
