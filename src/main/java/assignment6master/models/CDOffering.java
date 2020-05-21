package assignment6master.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "cdOfferings", catalog = "merit111")
public class CDOffering {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long offer_id;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CDAccount> cdAccount;
	
	private int term;
    private double interestRate;

    public CDOffering(){
        this.term = 0;
        this.interestRate = 0.01;
    }
    
    public CDOffering(int term, double interestRate){
        this.term = term;
        this.interestRate = interestRate;
    }

    public int getTerm(){
        return this.term;
    }

    public double getInterestRate(){
        return this.interestRate;
    }
    
    public void setTerm(int term) {
    	this.term = term;
    }
    
    public void setInterestRate(double interest) {
    	this.interestRate = interest;
    }

    public long getOffer_id() {
		return offer_id;
    }

	public void setOffer_id(long offer_id) {
		this.offer_id = offer_id;
	}

	public String toString(){
        return "Term: " + this.getTerm() + " Interest Rate: " + this.getInterestRate();
	}

	public List<CDAccount> getCdAccount() {
		return cdAccount;
	}

	public void setCdAccount(List<CDAccount> cdAccount) {
		this.cdAccount = cdAccount;
	}

	static CDOffering readFromString(String accountData) {
    	
    	String array1[] = accountData.split(",");
    	int fTerm = Integer.parseInt(array1[0]);
    	double fInterest = Double.parseDouble(array1[1]);
    	
    	CDOffering offeringX = new CDOffering(fTerm, fInterest);
    	return offeringX;
    	
    }
	
	public String writeToString() {
    	return this.term + "," + this.interestRate;
    }
}
