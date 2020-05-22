package assignment6master.models;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "accountHolder", catalog = "merit111")
public class AccountHolder implements Comparable<AccountHolder> {
	// Variables of Class
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "holder_id")
    private long id;
	
	@NotBlank(message = "First name required")
    private String firstName;
	
    private String middleName;
    
    @NotBlank(message = "Last name required")
    private String lastName;
    
    @NotBlank(message = "Social required")
    private String ssn;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "accountHolder")
    private AccountHolderContactDetails contact;
    
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	User user;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "holder_id", referencedColumnName = "holder_id")
    private List<CheckingAccount> checkingAccountsList;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "holder_id", referencedColumnName = "holder_id")
    private List<SavingsAccount> savingsAccountsList;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "holder_id", referencedColumnName = "holder_id")
    private List<CDAccount> cdAccountsList;

	// Constructors
    public AccountHolder() {
        this.firstName = "";
        this.middleName = "";
        this.lastName = "";
        this.ssn = "";
        this.savingsAccountsList = new ArrayList<>();
        this.checkingAccountsList = new ArrayList<>();
        this.cdAccountsList = new ArrayList<>();
    }

    public AccountHolder(String fn, String mn, String ln, String sn){
        this.firstName = fn;
        this.middleName = mn;
        this.lastName = ln;
        this.ssn = sn;
    }

    // Setters and Getters
    public void setFirstName(String fn){
        this.firstName = fn;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public void setMiddleName(String mn){
        this.middleName = mn;
    }

    public String getMiddleName(){
        return this.middleName;
    }

    public void setLastName(String ln){
        this.lastName = ln;
    }

    public String getLastName(){
        return this.lastName;
    }
    
    public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public List<CheckingAccount> getCheckingAccountsList() {
		return checkingAccountsList;
	}

	public void setCheckingAccountsList(List<CheckingAccount> checkingAccountsList) {
		this.checkingAccountsList = checkingAccountsList;
	}
	
	public List<SavingsAccount> getSavingsAccountsList() {
		return savingsAccountsList;
	}

	public void setSavingsAccountsList(List<SavingsAccount> savingsAccountsList) {
		this.savingsAccountsList = savingsAccountsList;
	}
	
	public List<CDAccount> getCdAccountsList() {
		return cdAccountsList;
	}

	public void setCdAccountsList(List<CDAccount> cdAccountsList) {
		this.cdAccountsList = cdAccountsList;
	}
	
	public AccountHolderContactDetails getContact() {
		return contact;
	}

	public void setContact(AccountHolderContactDetails contact) {
		this.contact = contact;
	}

	public int getNumberOfCheckingAccounts(){
        return this.checkingAccountsList.size();
    }
    
    public double getCheckingBalanceFromList() {
    	double tBalance = 0;
    	for(int x = 0; x < checkingAccountsList.size(); x++) {
    		tBalance += checkingAccountsList.get(x).getBalance();
    	}
    	return tBalance;
    }

    public int getNumberOfSavingsAccounts(){
        return this.savingsAccountsList.size();
    }
    
    public double getSavingsBalanceFromList() {
    	double tBalance = 0;
    	for(int x = 0; x < savingsAccountsList.size(); x++) {
    		tBalance += savingsAccountsList.get(x).getBalance();
    	}
    	return tBalance;
    }

    public int getNumberOfCDAccounts(){
        return this.cdAccountsList.size();
    }

    public double getCDAccountsBalanceFromList() {
    	double tBalance = 0;
    	for(int x = 0; x < cdAccountsList.size(); x++) {
    		tBalance += cdAccountsList.get(x).getBalance();
    	}
    	return tBalance;
    }

    public double getCombinedBalance(){
        double tBalance;
        tBalance = this.getCheckingBalanceFromList();
        tBalance += this.getSavingsBalanceFromList();
        tBalance += this.getCDAccountsBalanceFromList();
        return tBalance;
    }

    public String toString() {
        DecimalFormat format = new DecimalFormat("##.00");
        return "Name: " + this.firstName + " " + this.middleName + " " + this.lastName + "\n"
                + "SSN: " + this.ssn + "\n";
    }
    
    @Override
    public int compareTo(AccountHolder otherAccount) {
    	if(this.getCombinedBalance() > otherAccount.getCombinedBalance()) {
    		return 1;
    	} else
    		return -1;
    }


    
    
}
