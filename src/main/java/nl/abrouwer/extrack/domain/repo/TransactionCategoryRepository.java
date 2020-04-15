package nl.abrouwer.extrack.domain.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.abrouwer.extrack.domain.model.TransactionCategory;
import nl.abrouwer.extrack.domain.model.User;

public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, Long>
{
	public List<TransactionCategory> findByUser(User user);
}
