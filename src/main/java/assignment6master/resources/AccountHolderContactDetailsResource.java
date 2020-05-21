package assignment6master.resources;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import assignment6master.models.AccountHolder;
import assignment6master.models.AccountHolderContactDetails;
import assignment6master.models.CheckingAccount;
import assignment6master.repositories.AccountHolderContactDetailsRepo;
import assignment6master.repositories.AccountHolderRepo;
import assignment6master.repositories.CheckingAccountRepo;

@RequestMapping("/AccountHolderContactDetails")
@RestController
public class AccountHolderContactDetailsResource {
	
	@Autowired
	AccountHolderContactDetailsRepo accountHolderContact;
	
	@Autowired
	AccountHolderRepo accountHolderRepo;
	
	@Autowired
	CheckingAccountRepo checkingRepo;
	
	@GetMapping(value = "/all")
	public List<AccountHolderContactDetails> getAccountHolderContactDetails(){
		return accountHolderContact.findAll();
	}
	
	@GetMapping("/id/{id}")
	public Optional<AccountHolderContactDetails> getId(@PathVariable("id") final Long id) {
		return accountHolderContact.findById(id);
	}
	
	@GetMapping("/update/{phoneNo}/{email}/{first}/{middle}/{last}/{ssn}")
	public List<AccountHolderContactDetails> add(@PathVariable("phoneNo") final String phoneNo, @PathVariable("email") final String email
			, @PathVariable("first") final String first, @PathVariable("middle") final String middle, @PathVariable("last") final String last
			, @PathVariable("ssn") final String ssn){
		AccountHolderContactDetails accountContact = new AccountHolderContactDetails();
		
		accountContact.setPhoneNo(phoneNo);
		accountContact.setEmail(email);
		
		CheckingAccount checking = new CheckingAccount();
		checking.setBalance(1000);
		
		CheckingAccount checking2 = new CheckingAccount();
		checking2.setBalance(1000);
		
		AccountHolder account = new AccountHolder();
		
		account.setFirstName(first);
		account.setMiddleName(middle);
		account.setLastName(last);
		account.setSsn(ssn);
		
		account.setCheckingAccountsList(Arrays.asList(checking, checking2));
		accountHolderRepo.save(account);
		
		
		
		accountContact.setAccountHolder(account);
		
		
		accountHolderContact.save(accountContact);
		
		return accountHolderContact.findAll();
	}
	
}
