package assignment6master.models;

import java.text.DecimalFormat;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "savingsAcounts", catalog = "merit111")
public class SavingsAccount extends BankAccount {
	// Variables

    //Constructors
    public SavingsAccount(){
        super(0.00, 0.01);
    }

    public SavingsAccount(double savingB){
        super(savingB, 0.01);
    }

    //Methods

    public String toString(){
        DecimalFormat format = new DecimalFormat("##.00");
        return "\nSaving Account Balance: $" + format.format(this.getBalance()) + "\n"
                + "Saving Account Interest Rate: " + this.getInterestRate() + "\n"
                + "Saving Account Balance in 3 years: $" + format.format(this.futureValue(3));
    }
}

