package assignment6master.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import assignment6master.models.AccountHolder;
import assignment6master.models.AccountHolderContactDetails;
import assignment6master.models.CheckingAccount;
import assignment6master.models.NoSuchResourceFoundException;
import assignment6master.models.User;
import assignment6master.repositories.AccountHolderRepo;
import assignment6master.repositories.CheckingAccountRepo;
import assignment6master.repositories.UsersRepo;

@RequestMapping("/AccountHolder")
@RestController
public class AccountHolderResource {
	
	@Autowired
	AccountHolderRepo accountHolderRepo;
	
	@Autowired
	UsersRepo usersRepo;
	
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
	public AccountHolder addAccountHolder(@RequestBody @Valid AccountHolder accountHolder) {
		AccountHolderContactDetails contact = accountHolder.getContact();
		contact.setAccountHolder(accountHolder);
		User user = usersRepo.findById(accountHolder.getUser().getId()).orElse(null);
		accountHolder.setContact(contact);
		List<AccountHolder> arrayHold = user.getAccounts();
		arrayHold.add(accountHolder);
		user.setAccounts(arrayHold);
		accountHolder.setUser(user);
		return accountHolderRepo.save(accountHolder);
	}
	
	
}
