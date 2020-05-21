package assignment6master.models;

public class TransferTransaction extends Transaction {
	// used for creation the specific object of Transfer Transaction
	
	public TransferTransaction(long targetNum, long sourceNum, double amountNum, java.util.Date date) {
		super(targetNum, sourceNum, amountNum, date);
	}
	
	public void process() {
		BankAccount source = this.getSourceAccount();
		BankAccount target = this.getTargetAccount();
		double currentBal = source.getBalance();
		source.setBalance(currentBal - this.getAmount());
		double tcurrentBal = target.getBalance();
		target.setBalance(tcurrentBal + this.getAmount());
		DepositTransaction copy = new DepositTransaction(this.getTargetAccountNum(), this.getSourceAccountNum(), this.getAmount(), this.getTransactionDate());
		
		//source.addTransaction(copy);
		MeritBank.setBankAccount(source, this.getSourceAccountNum());
		MeritBank.setBankAccount(target, this.getTargetAccountNum());
	}
}
