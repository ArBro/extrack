package nl.abrouwer.extrack.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "TRANSACTION")
public class Transaction implements Serializable
{
	private static final long serialVersionUID = 1440466634235514814L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRA_TRANSACTION_NO")
	private long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "TRA_ACCOUNT_NO")
	private Account account;

	@Column(name = "TRA_MUTATION_DATE")
	private LocalDate mutationDate;

	@Column(name = "TRA_INTEREST_DATE")
	private LocalDate interestDate;

	@Column(name = "TRA_AMOUNT_DEC", scale = 2)
	private BigDecimal amount;

	@Column(name = "TRA_BALANCE_AFTER_DEC", scale = 2)
	private BigDecimal balanceAfterTransaction;

	@Column(name = "TRA_COUNTERPARTY_IBAN_CODE", length = 30)
	private String counterPartyIban;

	@Column(name = "TRA_COUNTERPARTY_NAME_DESC")
	private String counterPartyName;

	@Column(name = "TRA_COUNTERPARTY_BIC_CODE", length = 20)
	private String counterPartyBic;

	@Column(name = "TRA_TYPE_CODE", length = 10)
	private String type;

	@Column(name = "TRA_DESCRIPTION_DESC")
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name = "TRA_SOURCE_CODE", length = 10)
	private TransactionSource source;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "TRA_IMPORT_CODE")
	private TransactionImport transactionImport;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "TRA_CATEGORY_CODE")
	private TransactionCategory category;

	@Column(name = "TRA_CREATE_DATE")
	private LocalDateTime createDate;

	@Column(name = "TRA_UPDATE_DATE")
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


	public Long getId()
	{
		return id;
	}


	public Account getAccount()
	{
		return account;
	}

	public void setAccount(Account account)
	{
		this.account = account;
	}

	public LocalDate getMutationDate()
	{
		return mutationDate;
	}

	public void setMutationDate(LocalDate mutationDate)
	{
		this.mutationDate = mutationDate;
	}

	public LocalDate getInterestDate()
	{
		return interestDate;
	}

	public void setInterestDate(LocalDate interestDate)
	{
		this.interestDate = interestDate;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	public BigDecimal getBalanceAfterTransaction()
	{
		return balanceAfterTransaction;
	}

	public void setBalanceAfterTransaction(BigDecimal balanceAfterTransaction)
	{
		this.balanceAfterTransaction = balanceAfterTransaction;
	}

	public String getCounterPartyIban()
	{
		return counterPartyIban;
	}

	public void setCounterPartyIban(String counterPartyIban)
	{
		this.counterPartyIban = counterPartyIban;
	}

	public String getCounterPartyName()
	{
		return counterPartyName;
	}

	public void setCounterPartyName(String counterPartyName)
	{
		this.counterPartyName = counterPartyName;
	}

	public String getCounterPartyBic()
	{
		return counterPartyBic;
	}

	public void setCounterPartyBic(String counterPartyBic)
	{
		this.counterPartyBic = counterPartyBic;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public TransactionSource getSource()
	{
		return source;
	}

	public void setSource(TransactionSource source)
	{
		this.source = source;
	}


	public TransactionImport getTransactionImport()
	{
		return transactionImport;
	}


	public void setTransactionImport(TransactionImport transactionImport)
	{
		this.transactionImport = transactionImport;
	}

	public TransactionCategory getCategory()
	{
		return category;
	}

	public void setCategory(TransactionCategory category)
	{
		this.category = category;
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
