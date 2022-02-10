package cat.itacademy.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cat.itacademy.entity.Game;

@Repository
public interface GameRepository extends MongoRepository<Game, String> {
	
	public List<Game> findAllByPlayerId(Long playerId);
	
	public List<Game> deleteAllByPlayerId(Long playerId);
	
}
