package assignment6master.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users", catalog = "merit111")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int id;
	
	private String userName;
	
	private String password;
	
	private boolean active;
	
	private String roles;
	@OneToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	private List<AccountHolder> accounts;

	public User() {
		userName = "";
		password = "";
		active = true;
		roles = "";
	}
	
	public String getUserName() {
		return userName;
	}
	
	public List<AccountHolder> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<AccountHolder> accounts) {
		this.accounts = accounts;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getId() {
		return id;
	}

	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	
}
