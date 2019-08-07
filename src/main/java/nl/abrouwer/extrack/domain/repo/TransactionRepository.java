package nl.abrouwer.extrack.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.abrouwer.extrack.domain.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>
{

}
