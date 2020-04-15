package nl.abrouwer.extrack.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Currency;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNT")
public class Account implements Serializable
{
	private static final long serialVersionUID = 7339867326295410154L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ACC_ACCOUNT_NO")
	private long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ACC_USER_NO")
	private User user;

	@Column(name = "ACC_IBAN_CODE", length = 30, nullable = false)
	private String iban;

	@Column(name = "ACC_DESCRIPTION_DESC")
	private String description;

	@Column(name = "ACC_BIC_CODE", length = 20)
	private String bic;

	@Column(name = "ACC_CURRENCY_CODE", length = 3)
	private Currency currency;

	@Column(name = "ACC_CREATE_DATE")
	private LocalDateTime createDate;

	@Column(name = "ACC_UPDATE_DATE")
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


	public User getUser()
	{
		return user;
	}


	public void setUser(User user)
	{
		this.user = user;
	}


	public String getIban()
	{
		return iban;
	}


	public void setIban(String iban)
	{
		this.iban = iban;
	}


	public String getDescription()
	{
		return description;
	}


	public void setDescription(String description)
	{
		this.description = description;
	}


	public String getBic()
	{
		return bic;
	}


	public void setBic(String bic)
	{
		this.bic = bic;
	}


	public Currency getCurrency()
	{
		return currency;
	}


	public void setCurrency(Currency currency)
	{
		this.currency = currency;
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
