package cat.itacademy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cat.itacademy.entity.Role;
import cat.itacademy.entity.RoleEnum;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	public Optional<Role> findByRoleName(RoleEnum roleName);
	
}
