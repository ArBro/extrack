package nl.abrouwer.extrack.web.beans;

import java.util.Currency;

public class AccountCreateBean
{
	private String iban;
	private String description;
	private String bic;
	private Currency currency = Currency.getInstance("EUR");
	private String username;
	
	public AccountCreateBean()
	{
		
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

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}
}
