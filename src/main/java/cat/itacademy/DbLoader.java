package cat.itacademy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import cat.itacademy.entity.Game;
import cat.itacademy.entity.Player;
import cat.itacademy.entity.Role;
import cat.itacademy.entity.RoleEnum;
import cat.itacademy.entity.User;
import cat.itacademy.repository.GameRepository;
import cat.itacademy.repository.PlayerRepository;
import cat.itacademy.repository.RoleRepository;
import cat.itacademy.repository.UserRepository;

@Component
public class DbLoader implements ApplicationRunner {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	//Populate database at start
    public void run(ApplicationArguments args) {
    	
    	Role adminRole = new Role(RoleEnum.ROLE_ADMIN);
    	Role userRole = new Role(RoleEnum.ROLE_USER);
    	
    	roleRepository.save(adminRole);
    	roleRepository.save(userRole);
    	
    	User admin = new User("Admin", encoder.encode("password123"));
    	User user = new User("User", encoder.encode("password123"));
    	admin.addRole(adminRole);
    	user.addRole(userRole);
    	
    	userRepository.save(admin);
    	userRepository.save(user);
    	
    	Player player1 = new Player("Player 1");
    	Player player2 = new Player("Anònim");
    	
    	playerRepository.save(player1);
    	playerRepository.save(player2);
    	
    	gameRepository.deleteAll();
    	
    	Game game11 = new Game(player1.getId());
    	game11.setDice1(2);
    	game11.setDice2(5);
    	Game game12 = new Game(player1.getId());
    	game12.setDice1(3);
    	game12.setDice2(4);
    	
    	Game game21 = new Game(player2.getId());
    	game21.setDice1(3);
    	game21.setDice2(3);
    	Game game22 = new Game(player2.getId());
    	game22.setDice1(1);
    	game22.setDice2(1);
    	
    	gameRepository.insert(game11);
    	gameRepository.insert(game12);
    	gameRepository.insert(game21);
    	gameRepository.insert(game22);
    }
    
}
