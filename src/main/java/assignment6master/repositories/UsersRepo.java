package assignment6master.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import assignment6master.models.User;

public interface UsersRepo extends JpaRepository<User, Integer> {
	Optional<User> findByUserName(String userName);
}
