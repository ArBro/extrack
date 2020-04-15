package nl.abrouwer.extrack.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import nl.abrouwer.extrack.domain.model.Transaction;
import nl.abrouwer.extrack.domain.model.TransactionCategory;

public interface TransactionService
{
	public Page<Transaction> getAllTransactions(Pageable pageable);

	public List<TransactionCategory> getAllTransactionCategories();
}
