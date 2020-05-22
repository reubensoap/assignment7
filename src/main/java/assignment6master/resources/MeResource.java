package assignment6master.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import assignment6master.models.AccountHolder;
import assignment6master.models.CDAccount;
import assignment6master.models.CDOffering;
import assignment6master.models.CheckingAccount;
import assignment6master.models.ExceedsCombinedBalanceLimitException;
import assignment6master.models.NegativeAmountException;
import assignment6master.models.NoSuchResourceFoundException;
import assignment6master.models.SavingsAccount;
import assignment6master.models.User;
import assignment6master.repositories.CDAccountRepo;
import assignment6master.repositories.CDOfferingRepo;
import assignment6master.repositories.CheckingAccountRepo;
import assignment6master.repositories.SavingsAccountRepo;
import assignment6master.repositories.UsersRepo;
import assignment6master.security.JwtUtil;

@RequestMapping(value = "/Me")
@RestController
public class MeResource {
	
	@Autowired
	UsersRepo usersRepo;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	CheckingAccountRepo checkingRepo;
	
	@Autowired
	SavingsAccountRepo savingsRepo;
	
	@Autowired
	CDAccountRepo cdRepo;
	
	@Autowired
	CDOfferingRepo offeringRepo;

	@GetMapping(value = "/")
	public List<AccountHolder> getMyAccountData(@RequestHeader (name = "Authorization")String token){
		token = token.substring(7);
		User user = usersRepo.findByUserName(jwtUtil.extractUsername(token)).get();
		return user.getAccounts();
	}
	
	@GetMapping(value = "/CheckingAccounts")
	public List<CheckingAccount> getAllChecking(@RequestHeader (name = "Authorization")String token) throws NoSuchResourceFoundException {
		token = token.substring(7);
		User user = usersRepo.findByUserName(jwtUtil.extractUsername(token)).get();
		AccountHolder account = user.getAccounts().get(0);
		if(account == null) {
			throw new NoSuchResourceFoundException("Invalid id");
		}
		return account.getCheckingAccountsList();
	}
	
	@PostMapping(value = "/CheckingAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CheckingAccount addCheckingAccount(@RequestHeader (name = "Authorization")String token, @RequestBody CheckingAccount checking) throws NoSuchResourceFoundException, ExceedsCombinedBalanceLimitException, NegativeAmountException  {
		token = token.substring(7);
		User user = usersRepo.findByUserName(jwtUtil.extractUsername(token)).get();
		AccountHolder account = user.getAccounts().get(0);
		if(account == null) {
			throw new NoSuchResourceFoundException("Invalid id");
		}
		double combinedBalance = account.getCombinedBalance();
		if((combinedBalance + checking.getBalance()) > 250000) {
			throw new ExceedsCombinedBalanceLimitException("Exceeds account limit");
		} else if (checking.getBalance() < 0){
			throw new NegativeAmountException("Balance below 0");
		} else {
			checking.setHolder_id(account.getId());
			return checkingRepo.save(checking);
		}
	}
	
	@GetMapping(value = "/SavingsAcounts")
	public List<SavingsAccount> getAllSavings(@RequestHeader (name = "Authorization")String token) throws NoSuchResourceFoundException {
		token = token.substring(7);
		User user = usersRepo.findByUserName(jwtUtil.extractUsername(token)).get();
		AccountHolder account = user.getAccounts().get(0);
		if(account == null) {
			throw new NoSuchResourceFoundException("Invalid id");
		}
		return account.getSavingsAccountsList();
	}
	
	@PostMapping(value = "/SavingsAcounts")
	@ResponseStatus(HttpStatus.CREATED)
	public SavingsAccount addSavingsAccount(@RequestHeader (name = "Authorization")String token, @RequestBody SavingsAccount savings) throws NoSuchResourceFoundException, ExceedsCombinedBalanceLimitException, NegativeAmountException  {
		token = token.substring(7);
		User user = usersRepo.findByUserName(jwtUtil.extractUsername(token)).get();
		AccountHolder account = user.getAccounts().get(0);
		if(account == null) {
			throw new NoSuchResourceFoundException("Invalid id");
		}
		double combinedBalance = account.getCombinedBalance();
		if((combinedBalance + savings.getBalance()) > 250000) {
			throw new ExceedsCombinedBalanceLimitException("Exceeds account limit");
		} else if (savings.getBalance() < 0){
			throw new NegativeAmountException("Balance below 0");
		} else {
			savings.setHolder_id(account.getId());
			return savingsRepo.save(savings);
		}
	}
	
	@GetMapping(value = "/CDAccounts")
	public List<CDAccount> getAllById(@RequestHeader (name = "Authorization")String token) throws NoSuchResourceFoundException {
		token = token.substring(7);
		User user = usersRepo.findByUserName(jwtUtil.extractUsername(token)).get();
		AccountHolder account = user.getAccounts().get(0);
		if(account == null) {
			throw new NoSuchResourceFoundException("Invalid id");
		}
		return account.getCdAccountsList();
	}
	
	@PostMapping(value = "/CDAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CDAccount addCDAccount(@RequestHeader (name = "Authorization")String token, @RequestBody CDAccount cdaccount) throws NoSuchResourceFoundException, ExceedsCombinedBalanceLimitException, NegativeAmountException  {
		token = token.substring(7);
		User user = usersRepo.findByUserName(jwtUtil.extractUsername(token)).get();
		AccountHolder account = user.getAccounts().get(0);
		if(account == null) {
			throw new NoSuchResourceFoundException("Invalid id");
		}
		double combinedBalance = account.getCombinedBalance();
		if((combinedBalance + cdaccount.getBalance()) > 250000) {
			throw new ExceedsCombinedBalanceLimitException("Exceeds account limit");
		} else if (cdaccount.getBalance() < 0){
			throw new NegativeAmountException("Balance below 0");
		} else {
			CDOffering offer = offeringRepo.findById(cdaccount.getOffering().getOffer_id()).orElse(null);
			List<CDAccount> accountList = offer.getCdAccount();
			accountList.add(cdaccount);
			offer.setCdAccount(accountList);
			cdaccount.setOffering(offer);
			cdaccount.setHolder_id(account.getId());
			return cdRepo.save(cdaccount);
		}
	}
	
}
