package nl.abrouwer.extrack.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.abrouwer.extrack.domain.model.Account;
import nl.abrouwer.extrack.domain.model.User;
import nl.abrouwer.extrack.domain.repo.AccountRepository;

@Service
@Transactional
public class AccountServiceImpl implements AccountService
{
	private final AccountRepository accountRepository;


	public AccountServiceImpl(AccountRepository accountRepository)
	{
		this.accountRepository = accountRepository;
	}


	@Override
	public Account findByUserAndIban(User user, String iban)
	{
		return accountRepository.findByUserAndIban(user, iban);
	}

}
