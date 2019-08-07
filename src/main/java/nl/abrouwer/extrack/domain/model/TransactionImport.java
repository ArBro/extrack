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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "TRANSACTION_IMPORT")
public class TransactionImport implements Serializable
{
	private static final long serialVersionUID = 2559170699605229037L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IMP_IMPORT_NO")
	private long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "IMP_USER_NO")
	private User user;

	@Column(name = "IMP_FILE_DESC")
	private String file;

	@Column(name = "IMP_HASH_CODE", columnDefinition = "char", length = 32)
	private String hash;

	@Enumerated(EnumType.STRING)
	@Column(name = "IMP_STATUS_CODE", length = 20)
	private ImportStatus status;

	@OneToMany(mappedBy = "transactionImport", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Transaction> transactions = new ArrayList<>();

	@Column(name = "IMP_IMPORTED_DATE")
	private LocalDateTime importDate;

	@Column(name = "IMP_UPDATE_DATE")
	private LocalDateTime updateDate;


	@PrePersist
	public void onCreate()
	{
		this.importDate = LocalDateTime.now();
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


	public String getFile()
	{
		return file;
	}


	public void setFile(String file)
	{
		this.file = file;
	}


	public String getHash()
	{
		return hash;
	}


	public void setHash(String hash)
	{
		this.hash = hash;
	}


	public ImportStatus getStatus()
	{
		return status;
	}


	public void setStatus(ImportStatus status)
	{
		this.status = status;
	}


	public List<Transaction> getTransactions()
	{
		return transactions;
	}


	public LocalDateTime getImportDate()
	{
		return importDate;
	}


	public LocalDateTime getUpdateDate()
	{
		return updateDate;
	}
}
