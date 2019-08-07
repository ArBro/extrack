package nl.abrouwer.extrack.service;

import nl.abrouwer.extrack.domain.model.Account;
import nl.abrouwer.extrack.domain.model.User;

public interface AccountService
{
	public Account findByUserAndIban(User user, String iban);
}
