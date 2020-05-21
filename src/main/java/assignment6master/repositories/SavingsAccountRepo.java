package assignment6master.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import assignment6master.models.SavingsAccount;

public interface SavingsAccountRepo extends JpaRepository<SavingsAccount, Long>{

}
