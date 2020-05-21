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
import assignment6master.models.CDAccount;
import assignment6master.models.CDOffering;
import assignment6master.models.ExceedsCombinedBalanceLimitException;
import assignment6master.models.NegativeAmountException;
import assignment6master.models.NoSuchResourceFoundException;
import assignment6master.models.SavingsAccount;
import assignment6master.repositories.AccountHolderRepo;
import assignment6master.repositories.CDAccountRepo;
import assignment6master.repositories.CDOfferingRepo;

@RestController
@RequestMapping(value = "/AccountHolder")
public class CDAccountResource {
	
	@Autowired
	CDAccountRepo cdaccountRepo;
	
	@Autowired
	CDOfferingRepo cdofferingRepo;
	
	@Autowired
	AccountHolderRepo accountHolderRepo;
	
	@GetMapping(value = "/CDAccounts")
	public List<CDAccount> getAll(){
		return cdaccountRepo.findAll();
	}
	
	@GetMapping(value = "/{id}/CDAccounts")
	public List<CDAccount> getAllById(@PathVariable("id") final long holder_id) throws NoSuchResourceFoundException {
		if(holder_id > accountHolderRepo.count()) {
			throw new NoSuchResourceFoundException("Invalid id");
		}
		AccountHolder accountHolder = accountHolderRepo.getOne(holder_id);
		return accountHolder.getCdAccountsList();
	}
	
	@PostMapping(value = "/{id}/{offer_id}/CDAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CDAccount addCDAccount(@PathVariable("id") final long holder_id, @PathVariable("offer_id") final long offer_id, @RequestBody CDAccount cdaccount) throws NoSuchResourceFoundException, ExceedsCombinedBalanceLimitException, NegativeAmountException  {
		if(holder_id > accountHolderRepo.count()) {
			throw new NoSuchResourceFoundException("Invalid id");
		}
		AccountHolder accountHolder = accountHolderRepo.getOne(holder_id);
		double combinedBalance = accountHolder.getCombinedBalance();
		if((combinedBalance + cdaccount.getBalance()) > 250000) {
			throw new ExceedsCombinedBalanceLimitException("Exceeds account limit");
		} else if (cdaccount.getBalance() < 0){
			throw new NegativeAmountException("Balance below 0");
		} else {
			CDOffering offering = cdofferingRepo.getOne(offer_id);
			cdaccount.setOffering(offering);
			cdaccount.setHolder_id(holder_id);
			return cdaccountRepo.save(cdaccount);
		}
	}
	
	
}
