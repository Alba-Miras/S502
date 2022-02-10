package cat.itacademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cat.itacademy.entity.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
	
	public boolean existsByName(String name);
	
}