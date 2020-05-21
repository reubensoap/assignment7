package assignment6master.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import assignment6master.models.AccountHolder;
import assignment6master.models.AccountHolderContactDetails;

public interface AccountHolderContactDetailsRepo extends JpaRepository<AccountHolderContactDetails, Long> {
	List<AccountHolderContactDetails> findById(long id);
}
