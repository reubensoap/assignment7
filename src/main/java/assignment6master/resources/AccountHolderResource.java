package assignment6master.resources;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import assignment6master.models.NoSuchResourceFoundException;
import assignment6master.repositories.AccountHolderRepo;
import assignment6master.repositories.CheckingAccountRepo;

@RequestMapping("/AccountHolder")
@RestController
public class AccountHolderResource {
	
	@Autowired
	AccountHolderRepo accountHolderRepo;
	
	@GetMapping(value = "/")
	public List<AccountHolder> getAll() {
		return accountHolderRepo.findAll();
	}
	
	@GetMapping("/{id}") 
	public List<AccountHolder> getId(@PathVariable("id") final long holder_id) throws NoSuchResourceFoundException{
		if(holder_id > accountHolderRepo.count()) {
			throw new NoSuchResourceFoundException("Invalid id");
		}
		return accountHolderRepo.findById(holder_id);
	}
	
	@PostMapping(value = "/")
	@ResponseStatus(HttpStatus.CREATED)
	public AccountHolder add(@RequestBody @Valid AccountHolder account){
		AccountHolderContactDetails contact = account.getContact();
		contact.setAccountHolder(account);
		accountHolderRepo.save(account);
		return account;
	}
	
	
}
