package assignment6master.models;

import java.text.DecimalFormat;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cdaccountAccounts", catalog = "merit111")
public class CDAccount extends BankAccount {
	//public CDOffering offering = new CDOffering(1, 0.01);
	
	private int term;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "offer_id", referencedColumnName = "offer_id")
	private CDOffering offering;
	
	// used for creation thru the REST API
	public CDAccount(){
        super(0.00, 0.01);
        this.term = 0;
    }
	
	public CDAccount(double balance) {
		super(balance, 0.01);
	}
	
    public CDAccount(double balance, double interestRate, int term) {
    	super(balance, interestRate);
    	this.term = term;
    }

    public CDAccount(CDOffering offering, double openingBalance){
        super(openingBalance, offering.getInterestRate());
        this.term = offering.getTerm();
    }
    
    // Getters and Setters
    
    public int getTerm(){
        return this.term;
    }
    
    public void setTerm(int term) {
    	this.term = term;
    }

	public CDOffering getOffering() {
		return offering;
	}

	public void setOffering(CDOffering offering) {
		this.offering = offering;
		this.term = offering.getTerm();
		this.setInterestRate(offering.getInterestRate());
	}

	// override from BankAccount withdraw , deposit , futureValue
    // no need for withdraw for CDAccount
    public boolean withdraw(double amount){
        return false;
    }
	
	public boolean deposit(double amount) {
		// Desosit only allowed during creation
		if(this.getBalance() == 0) {
			try {
				if(amount <= 0){
		        	throw new NegativeAmountException("Please Deposit a positive amount.");
		        }
			} catch (NegativeAmountException e) {
				System.out.println(e);
				return false;
			}
	    
	        try {
	        	if(amount > 1000) {
		        	throw new ExceedsFraudSuspicionLimitException("Transaction requires review, thanks for your patience.");
	        	}
	        } catch(ExceedsFraudSuspicionLimitException e) {
	        	java.util.Date fDate = new java.util.Date();
		        DepositTransaction newTrans = new DepositTransaction(-1, this.getAccountNumber(), amount, fDate);
		        MeritBank.processTransaction(newTrans);
	        	System.out.println(e);
	        	FraudQueue.addTransaction(newTrans);
	        	return true;
	        }
	        java.util.Date fDate = new java.util.Date();
	        DepositTransaction newTrans = new DepositTransaction(-1, this.getAccountNumber(), amount, fDate);
	        MeritBank.processTransaction(newTrans);
	        return true;
		}
        return false;
    }
	
	public double futureValue() {
        double fv = MeritBank.recursiveFutureValue(this.getBalance(), this.getTerm(), this.getInterestRate());
        return fv;
	}
	
	public String toString(){
        DecimalFormat format = new DecimalFormat("##.00");
        return "\nCDAccount Balance: $" + format.format(this.getBalance()) + "\n"
                + "CDAccount Interest Rate: " + this.getInterestRate() + "\n"
                + "CDAccount Balance in 3 years: $" + format.format(this.futureValue(3));
    }
}
