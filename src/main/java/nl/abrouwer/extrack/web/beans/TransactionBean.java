package nl.abrouwer.extrack.web.beans;

import java.util.ArrayList;
import java.util.List;

import nl.abrouwer.extrack.domain.model.Transaction;

public class TransactionBean
{
	private List<Transaction> transactions = new ArrayList<>();

	public TransactionBean(List<Transaction> transactions)
	{
		this.transactions = transactions;
	}

	public List<Transaction> getTransactions()
	{
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions)
	{
		this.transactions = transactions;
	}
}
