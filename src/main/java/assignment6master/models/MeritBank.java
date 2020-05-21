package assignment6master.models;

import java.util.Arrays;
import java.util.List;

public class MeritBank {
	static AccountHolder accountHolder[] = new AccountHolder[1];
    static CDOffering cdOfferingHolder[] = new CDOffering[1];
    static long lastAccountNumber = 1;
    
    static long accountHolderAccountNum = 1;

    public static void addAccountHolder(AccountHolder accountHolderx){
        accountHolder[accountHolder.length - 1] = accountHolderx;
        AccountHolder arrayTemp[] = new AccountHolder[accountHolder.length + 1];
        for(int x = 0; x < accountHolder.length; x++) {
        	arrayTemp[x] = accountHolder[x];
        }
        accountHolder = arrayTemp;
    }

    public static AccountHolder[] getAccountHolders() {
        return accountHolder;
    }
    
    public static AccountHolder getAccountHolder(long id) {
    	int converted = ((int)id) - 1;
    	return MeritBank.accountHolder[converted];
    }

    public static CDOffering[] getCDOfferings() {
        return cdOfferingHolder;
    }

    // used for get Best CDOffer inside the cdOfferingHolder array
    public static CDOffering getBestCDOffering(double depositAmount){
        int best = 0;
        double tv;
        double fv;
        if (cdOfferingHolder.length < 2){
            System.out.println("Unable to complete action. Need more CD Offers.");
            return cdOfferingHolder[0];
        }
        for(int x = 0; x < cdOfferingHolder.length; x++){
            tv = MeritBank.recursiveFutureValue(depositAmount, cdOfferingHolder[x].getTerm(), cdOfferingHolder[x].getInterestRate());
            fv = MeritBank.recursiveFutureValue(depositAmount, cdOfferingHolder[best].getTerm(), cdOfferingHolder[best].getInterestRate());
            if (tv > fv){
                best = x;
            }
        }
        return cdOfferingHolder[best];
    }

    // used for get Second Best CDOffer inside the cdOfferingHolder array
    public static CDOffering getSecondBestCDOffering(double depositAmount){
        int best = 0;
        int second = 0;
        double tv;
        double fv;
        double sv;
        if (cdOfferingHolder.length < 2){
            System.out.println("Unable to complete action. Need more CD Offers.");
            return null;
        }
        for(int x = 0; x < cdOfferingHolder.length; x++){
            tv = MeritBank.recursiveFutureValue(depositAmount, cdOfferingHolder[x].getTerm(), cdOfferingHolder[x].getInterestRate());
            fv = MeritBank.recursiveFutureValue(depositAmount, cdOfferingHolder[best].getTerm(), cdOfferingHolder[best].getInterestRate());
            sv = MeritBank.recursiveFutureValue(depositAmount, cdOfferingHolder[second].getTerm(), cdOfferingHolder[second].getInterestRate());
            if (tv > fv){
                second = best;
                best = x;
            }
            else if(tv > sv && tv != fv){
                second = x;
            }
        }
        return cdOfferingHolder[second];
    }

    // Clears the cdOfferingHolder array in order to get ready for another set of 
    // CDOfferings
    public static void clearCDOfferings(){
        CDOffering newArr[] = null;
        cdOfferingHolder = newArr;
    }

    // Sets a new array of CDOfferings in the cdOfferingHolder 
    public static void setCDOfferings(CDOffering[] offerings){
        cdOfferingHolder = offerings;
    }

    // returns the balances of all AccountHolders inside the Merit
    // Bank class
    public static double totalBalances(){
        double tBalance = 0;
        for(int x = 0; x < accountHolder.length - 1; x++){
            tBalance += accountHolder[x].getCombinedBalance();
        }
        return tBalance;
    }

    public static long getNextAccountNumber(){
    	long newNum = MeritBank.lastAccountNumber;
    	MeritBank.lastAccountNumber++;
    	return newNum;
    }
    
    public static void setNextAccountNumber(long nextAccountNumber) {
    	MeritBank.lastAccountNumber = nextAccountNumber;
    }
    
    public static long getNextAccountHolderAccountNumber() {
    	long newNum = MeritBank.accountHolderAccountNum;
    	MeritBank.accountHolderAccountNum++;
    	return newNum;
    }
    
    // sorts AccountHolders based on CompareTo override in AccountHolder class
    // starts at index 0 and ends at the second to last index due to last
    // index being null
    public static AccountHolder[] sortAccountHolders() {
    	Arrays.sort(accountHolder, 0, accountHolder.length - 1);
    	return accountHolder;
    }
    
    public static List<Transaction> getFraudQueue(){
    	return FraudQueue.getTransactions();
    }
    
    public static BankAccount getBankAccount(long accountID) {
    	BankAccount found = null;
    	for(int x = 0; x < accountHolder.length - 1; x++) {
    		
    		CheckingAccount checkingArray[] = accountHolder[x].getCheckingAccounts();
    		
    		for(int y = 0; y < checkingArray.length - 1; y++) {
    			if(accountID == checkingArray[y].getAccountNumber()) {
    				found = checkingArray[y];
    				return found;
    			}
    		}
    		
    		SavingsAccount savingsArray[] = accountHolder[x].getSavingsAccounts();
    		
    		for(int y = 0; y < savingsArray.length - 1; y++) {
    			if(accountID == savingsArray[y].getAccountNumber()) {
    				found = savingsArray[y];
    				return found;
    			}
    		}
    		
    		CDAccount cdAccountArray[] = accountHolder[x].getCDAccounts();
    		
    		for(int y = 0; y < cdAccountArray.length - 1; y++) {
    			if(accountID == cdAccountArray[y].getAccountNumber()) {
    				found = cdAccountArray[y];
    				return found;
    			}
    		}
    		
    	}
    	
    	return found;
    }
    
    public static boolean setBankAccount(BankAccount source, long accountID) {
    	
    	for(int x = 0; x < accountHolder.length - 1; x++) {
    		
    		CheckingAccount checkingArray[] = accountHolder[x].getCheckingAccounts();
    		
    		for(int y = 0; y < checkingArray.length - 1; y++) {
    			if(accountID == checkingArray[y].getAccountNumber()) {
    				checkingArray[y] = (CheckingAccount) source;
    				return true;
    			}
    		}
  
    	}
    	
    	return false;
    }
    
    public static boolean setBankAccount(CheckingAccount source, long accountID) {
    	
    	for(int x = 0; x < accountHolder.length - 1; x++) {
    		
    		CheckingAccount checkingArray[] = accountHolder[x].getCheckingAccounts();
    		
    		for(int y = 0; y < checkingArray.length - 1; y++) {
    			if(accountID == checkingArray[y].getAccountNumber()) {
    				checkingArray[y] = (CheckingAccount) source;
    				return true;
    			}
    		}
  
    	}
    	
    	return false;
    }
    
    public static boolean setBankAccount(SavingsAccount source, long accountID) {
    	
    	for(int x = 0; x < accountHolder.length - 1; x++) {
    		
    		SavingsAccount savingsArray[] = accountHolder[x].getSavingsAccounts();
    		
    		for(int y = 0; y < savingsArray.length - 1; y++) {
    			if(accountID == savingsArray[y].getAccountNumber()) {
    				savingsArray[y] = (SavingsAccount) source;
    				return true;
    			}
    		}
    		
    	}
    	
    	return false;
    }

	public static boolean setBankAccount(CDAccount source, long accountID) {
		
		for(int x = 0; x < accountHolder.length - 1; x++) {
			
			CDAccount cdAccountArray[] = accountHolder[x].getCDAccounts();
			
			for(int y = 0; y < cdAccountArray.length - 1; y++) {
				if(accountID == cdAccountArray[y].getAccountNumber()) {
					cdAccountArray[y] = (CDAccount) source;
					return true;
				}
			}
			
		}
		
		return false;
	}
	
	public static double recursiveFutureValue(double amount, int years, double interestRate) {
		amount = amount * (1 + interestRate);
		if(years != 1) {
			return recursiveFutureValue(amount, years - 1, interestRate);
		} else {
			return amount;
		}
	}
	
	public static boolean processTransaction(Transaction transaction) {
    	transaction.process();
    	return true;
    }
}
