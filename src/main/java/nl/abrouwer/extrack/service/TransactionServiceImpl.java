package nl.abrouwer.extrack.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.abrouwer.extrack.domain.model.Transaction;
import nl.abrouwer.extrack.domain.model.User;
import nl.abrouwer.extrack.domain.repo.AccountRepository;
import nl.abrouwer.extrack.domain.repo.TransactionRepository;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService
{
	private final AccountRepository accountRepository;

	private final TransactionRepository transactionRepository;


	public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository)
	{
		this.accountRepository = accountRepository;
		this.transactionRepository = transactionRepository;
	}

	@Override
	public Page<Transaction> getAllTransactionsForUser(Pageable pageable, User user)
	{
		List<Long> accounts = accountRepository.findAccountIdAloneByUser(user);
		Page<Transaction> transactions = transactionRepository.findByAccountIn(accounts, pageable);

		return transactions;
	}

}
