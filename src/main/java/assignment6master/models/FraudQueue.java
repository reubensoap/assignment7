package assignment6master.models;

import java.util.ArrayList;
import java.util.List;

public class FraudQueue {
	static List<Transaction> fraudList = new ArrayList<Transaction> ();

	public static void addTransaction(Transaction transaction) {
		fraudList.add(transaction);
	}
	
	public static List<Transaction> getTransactions() {
		return FraudQueue.fraudList;
	}
	
	public static Transaction getTransaction() {
		return FraudQueue.fraudList.get(0);
	}
}
