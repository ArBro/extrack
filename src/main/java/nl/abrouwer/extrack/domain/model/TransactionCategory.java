package nl.abrouwer.extrack.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "TRANSACTION_CATEGORY")
public class TransactionCategory implements Serializable
{
	private static final long serialVersionUID = 5995698186702623785L;

	@Id
	@Column(name = "TRC_CATEGORY_NO")
	private Long id;

	@Column(name = "TRC_DESCRIPTION_DESC")
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name = "TRC_TYPE_CODE", length = 10)
	private TransactionCategoryType type;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private List<Transaction> transactions;

	@Type(type = "true_false")
	@Column(name = "TRC_ACTIVE_BOOL", columnDefinition = "char", length = 1)
	private Boolean active;

	@Column(name = "TRC_CREATE_DATE")
	private LocalDateTime createDate;

	@Column(name = "TRC_UPDATE_DATE")
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


	public String getDescription()
	{
		return description;
	}


	public void setDescription(String description)
	{
		this.description = description;
	}


	public TransactionCategoryType getType()
	{
		return type;
	}


	public void setType(TransactionCategoryType type)
	{
		this.type = type;
	}


	public Boolean getActive()
	{
		return active;
	}


	public void setActive(Boolean active)
	{
		this.active = active;
	}


	public List<Transaction> getTransactions()
	{
		if (this.transactions == null)
		{
			return new ArrayList<>();
		}

		return transactions;
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
