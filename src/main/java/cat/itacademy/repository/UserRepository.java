package cat.itacademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cat.itacademy.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	public boolean existsByUserName(String userName);

	public User findByUserName(String userName);

}