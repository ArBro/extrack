package nl.abrouwer.extrack.service;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.abrouwer.extrack.domain.model.Transaction;
import nl.abrouwer.extrack.domain.model.TransactionCategory;
import nl.abrouwer.extrack.domain.model.User;
import nl.abrouwer.extrack.domain.repo.TransactionCategoryRepository;
import nl.abrouwer.extrack.domain.repo.TransactionRepository;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService
{
	private final UserService userService;

	private final TransactionRepository transactionRepository;

	private final TransactionCategoryRepository transactionCategoryRepository;


	public TransactionServiceImpl(UserService userService, TransactionRepository transactionRepository,
			TransactionCategoryRepository transactionCategoryRepository)
	{
		this.userService = userService;
		this.transactionRepository = transactionRepository;
		this.transactionCategoryRepository = transactionCategoryRepository;
	}

	@Override
	public Page<Transaction> getAllTransactions(Pageable pageable)
	{
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.getUser(username);

		if (user != null && !user.getAccounts().isEmpty())
		{
			return transactionRepository.findByAccountIn(user.getAccounts(), pageable);
		}

		return new PageImpl<>(Collections.emptyList());
	}


	@Override
	public List<TransactionCategory> getAllTransactionCategories()
	{
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.getUser(username);

		if (user != null && !user.getAccounts().isEmpty())
		{
			return transactionCategoryRepository.findByUser(user);
		}

		return Collections.emptyList();
	}

}
