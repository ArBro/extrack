package nl.abrouwer.extrack.service;

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.abrouwer.extrack.domain.model.Account;
import nl.abrouwer.extrack.domain.model.User;
import nl.abrouwer.extrack.domain.repo.AccountRepository;

@Service
@Transactional
public class AccountServiceImpl implements AccountService
{
	private final UserService userService;

	private final AccountRepository accountRepository;


	public AccountServiceImpl(UserService userService, AccountRepository accountRepository)
	{
		this.userService = userService;
		this.accountRepository = accountRepository;
	}


	@Override
	public List<Account> findAll()
	{
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.getUser(username);

		if (user != null)
		{
			return accountRepository.findByUser(user);
		}

		return Collections.emptyList();
	}


	@Override
	public Account findByUserAndIban(User user, String iban)
	{
		return accountRepository.findByUserAndIban(user, iban);
	}

}
