package nl.abrouwer.extrack.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import nl.abrouwer.extrack.domain.model.Transaction;
import nl.abrouwer.extrack.domain.model.User;

public interface TransactionService
{
	Page<Transaction> getAllTransactionsForUser(Pageable pagable, User user);
}
