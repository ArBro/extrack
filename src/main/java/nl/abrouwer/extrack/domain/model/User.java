package nl.abrouwer.extrack.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User
{
	@Id
	@Column(name = "USR_ID_CODE")
	private long id;

	@Column(name = "USR_NAME_DESC")
	private String username;

	@Column(name = "USR_PASSWORD_CODE")
	private String password;


	public long getId()
	{
		return id;
	}


	public void setId(long id)
	{
		this.id = id;
	}


	public String getPassword()
	{
		return password;
	}


	public void setPassword(String password)
	{
		this.password = password;
	}


	public String getUsername()
	{
		return username;
	}


	public void setUsername(String username)
	{
		this.username = username;
	}

}
