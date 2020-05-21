package assignment6master.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import assignment6master.models.AccountHolder;
import assignment6master.models.CheckingAccount;
import assignment6master.models.ExceedsCombinedBalanceLimitException;
import assignment6master.models.NegativeAmountException;
import assignment6master.models.NoSuchResourceFoundException;
import assignment6master.models.SavingsAccount;
import assignment6master.repositories.AccountHolderRepo;
import assignment6master.repositories.CheckingAccountRepo;
import assignment6master.repositories.SavingsAccountRepo;

@RequestMapping("/AccountHolder")
@RestController
public class SavingsAccountResource {

	@Autowired
	SavingsAccountRepo savingsAccountRepo;
	
	@Autowired
	AccountHolderRepo accountHolderRepo;
	
	@GetMapping(value = "/SavingsAccounts")
	public List<SavingsAccount> getAll(){
		return savingsAccountRepo.findAll();
	}
	
	@GetMapping(value = "/{id}/SavingsAccounts")
	public List<SavingsAccount> getAllById(@PathVariable("id") final long holder_id) throws NoSuchResourceFoundException {
		if(holder_id > accountHolderRepo.count()) {
			throw new NoSuchResourceFoundException("Invalid id");
		}
		AccountHolder accountHolder = accountHolderRepo.getOne(holder_id);
		return accountHolder.getSavingsAccountsList();
	}
	
	@PostMapping(value = "/{id}/SavingsAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public SavingsAccount addSavingsAccount(@PathVariable("id") final long holder_id, @RequestBody SavingsAccount savings) throws NoSuchResourceFoundException, ExceedsCombinedBalanceLimitException, NegativeAmountException  {
		if(holder_id > accountHolderRepo.count()) {
			throw new NoSuchResourceFoundException("Invalid id");
		}
		AccountHolder accountHolder = accountHolderRepo.getOne(holder_id);
		double combinedBalance = accountHolder.getCombinedBalance();
		if((combinedBalance + savings.getBalance()) > 250000) {
			throw new ExceedsCombinedBalanceLimitException("Exceeds account limit");
		} else if (savings.getBalance() < 0){
			throw new NegativeAmountException("Balance below 0");
		} else {
			savings.setHolder_id(holder_id);
			return savingsAccountRepo.save(savings);
		}
	}
}
