package nl.abrouwer.extrack.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.abrouwer.extrack.domain.model.User;

public interface UserRepository extends JpaRepository<User, Long>
{
	User findByUsername(String username);
}
