package nl.abrouwer.extrack.service;

import java.util.List;

import nl.abrouwer.extrack.domain.model.Account;
import nl.abrouwer.extrack.domain.model.User;

public interface AccountService
{
	public List<Account> findAll();

	public Account findByUserAndIban(User user, String iban);
}
