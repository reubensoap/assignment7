package assignment6master.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import assignment6master.models.CheckingAccount;

public interface CheckingAccountRepo extends JpaRepository<CheckingAccount, Long> {
	
}
