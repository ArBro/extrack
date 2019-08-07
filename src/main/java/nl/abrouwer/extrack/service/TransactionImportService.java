package nl.abrouwer.extrack.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import nl.abrouwer.extrack.domain.model.TransactionImport;

public interface TransactionImportService
{
	public List<TransactionImport> getAllTransactionImportsForUser(String username);

	public void importTransactions(MultipartFile file);
}
