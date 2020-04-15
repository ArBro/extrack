package nl.abrouwer.extrack.domain.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import nl.abrouwer.extrack.domain.model.Account;
import nl.abrouwer.extrack.domain.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>
{
	public Page<Transaction> findByAccountIn(List<Account> accounts, Pageable pageable);
}
