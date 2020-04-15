package nl.abrouwer.extrack.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class User implements Serializable
{
	private static final long serialVersionUID = 6634276093814759411L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USR_USER_NO")
	private long id;

	@Column(name = "USR_NAME_DESC", unique = true)
	private String username;

	@Column(name = "USR_PASSWORD_CODE")
	private String password;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TransactionImport> transactionImports = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Account> accounts = new ArrayList<>();

	@Column(name = "USR_CREATE_DATE")
	private LocalDateTime createDate;

	@Column(name = "USR_UPDATE_DATE")
	private LocalDateTime updateDate;


	@PrePersist
	public void onCreate()
	{
		this.createDate = LocalDateTime.now();
		onUpdate();
	}


	@PreUpdate
	public void onUpdate()
	{
		this.updateDate = LocalDateTime.now();
	}


	public long getId()
	{
		return id;
	}


	public String getUsername()
	{
		return username;
	}


	public void setUsername(String username)
	{
		this.username = username;
	}


	public String getPassword()
	{
		return password;
	}


	public void setPassword(String password)
	{
		this.password = password;
	}


	public List<TransactionImport> getTransactionImports()
	{
		return transactionImports;
	}


	public List<Account> getAccounts()
	{
		return accounts;
	}


	public LocalDateTime getCreateDate()
	{
		return createDate;
	}


	public LocalDateTime getUpdateDate()
	{
		return updateDate;
	}

}
