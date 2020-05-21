package assignment6master.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import assignment6master.models.AccountHolder;

public interface AccountHolderRepo extends JpaRepository<AccountHolder, Long>{
	List<AccountHolder> findById(long id);
}
