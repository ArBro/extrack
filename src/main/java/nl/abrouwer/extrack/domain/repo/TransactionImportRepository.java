package nl.abrouwer.extrack.domain.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.abrouwer.extrack.domain.model.TransactionImport;
import nl.abrouwer.extrack.domain.model.User;

public interface TransactionImportRepository extends JpaRepository<TransactionImport, Long>
{
	public List<TransactionImport> findAllByUser(User user);

	public TransactionImport findByHash(String hashCode);
}
