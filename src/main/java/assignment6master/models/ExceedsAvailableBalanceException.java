package assignment6master.models;

public class ExceedsAvailableBalanceException extends Exception {
	public ExceedsAvailableBalanceException(String message) {
		super(message);
	}
}
