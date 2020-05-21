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
import assignment6master.models.AccountHolderContactDetails;
import assignment6master.models.CheckingAccount;
import assignment6master.models.ExceedsCombinedBalanceLimitException;
import assignment6master.models.NegativeAmountException;
import assignment6master.models.NoSuchResourceFoundException;
import assignment6master.repositories.AccountHolderRepo;
import assignment6master.repositories.CheckingAccountRepo;

@RequestMapping("/AccountHolder")
@RestController
public class CheckingAccountResource {

	@Autowired
	CheckingAccountRepo checkingRepo;
	
	@Autowired
	AccountHolderRepo accountHolderRepo;
	
	@GetMapping(value = "/CheckingAccounts")
	public List<CheckingAccount> getAccountHolderContactDetails(){
		return checkingRepo.findAll();
	}
	
	@GetMapping(value = "/{id}/CheckingAccounts")
	public List<CheckingAccount> getAllById(@PathVariable("id") final long holder_id) throws NoSuchResourceFoundException {
		if(holder_id > accountHolderRepo.count()) {
			throw new NoSuchResourceFoundException("Invalid id");
		}
		AccountHolder accountHolder = accountHolderRepo.getOne(holder_id);
		return accountHolder.getCheckingAccountsList();
	}
	
	@PostMapping(value = "/{id}/CheckingAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CheckingAccount addCheckingAccount(@PathVariable("id") final long holder_id, @RequestBody CheckingAccount checking) throws NoSuchResourceFoundException, ExceedsCombinedBalanceLimitException, NegativeAmountException  {
		if(holder_id > accountHolderRepo.count()) {
			throw new NoSuchResourceFoundException("Invalid id");
		}
		AccountHolder accountHolder = accountHolderRepo.getOne(holder_id);
		double combinedBalance = accountHolder.getCombinedBalance();
		if((combinedBalance + checking.getBalance()) > 250000) {
			throw new ExceedsCombinedBalanceLimitException("Exceeds account limit");
		} else if (checking.getBalance() < 0){
			throw new NegativeAmountException("Balance below 0");
		} else {
			
			checking.setHolder_id(holder_id);
			return checkingRepo.save(checking);
		}
		//return checking;
	}
}
