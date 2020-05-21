package assignment6master.models;

import java.text.SimpleDateFormat;

public abstract class Transaction {
	//variables
	java.util.Date date;
	private double amount;
	private long sourceAccountNum = 0;
	private long targetAccountNum = 0;
	
	// Constructor
	public Transaction(long targetNum, long sourceNum, double amountNum, java.util.Date date) {
		this.sourceAccountNum = sourceNum;
		this.targetAccountNum = targetNum;
		this.amount = amountNum;
		this.date = date;
	}
	
	// Getters and Setters
	
	public BankAccount getSourceAccount() {
		BankAccount sourceOb = MeritBank.getBankAccount(sourceAccountNum);
		return sourceOb;
	}
	
	public void setSourceAccount(long source) {
		this.sourceAccountNum = source;
	}
	
	public BankAccount getTargetAccount() {
		BankAccount targetOb = MeritBank.getBankAccount(targetAccountNum);
		return targetOb;
	}
	
	public void setTargetAccount(long target) {
		this.targetAccountNum = target;
	}
	
	public double getAmount() {
		return this.amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public long getTargetAccountNum() {
		return this.targetAccountNum;
	}
	
	public long getSourceAccountNum() {
		return this.sourceAccountNum;
	}
	
	public java.util.Date getTransactionDate(){
		return this.date;
	}
	
	public void setTransactionDate(java.util.Date date) {
		this.date = date;
	}
	
	public String toString() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
    	return this.targetAccountNum + "," + this.sourceAccountNum + "," + this.amount
    			+ "," + dateFormatter.format(this.date);
	}
	
	// This method will be implemented in the child Transaction classes
	public abstract void process();
}
