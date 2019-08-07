package nl.abrouwer.extrack.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.abrouwer.extrack.domain.model.Account;
import nl.abrouwer.extrack.domain.model.User;

public interface AccountRepository extends JpaRepository<Account, Long>
{
	public Account findByUserAndIban(User user, String iban);
}
