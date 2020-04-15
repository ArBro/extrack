package nl.abrouwer.extrack.domain.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import nl.abrouwer.extrack.domain.model.Account;
import nl.abrouwer.extrack.domain.model.User;

public interface AccountRepository extends JpaRepository<Account, Long>
{
	public List<Account> findByUser(User user);

	public Account findByUserAndIban(User user, String iban);

	@Query(value = "select acc.id from Account acc where acc.user= user.id")
	List<Long> findAccountIdAloneByUser(@Param("user") User user);

}
