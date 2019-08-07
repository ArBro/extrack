package nl.abrouwer.extrack.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import au.com.bytecode.opencsv.CSVReader;
import nl.abrouwer.extrack.domain.model.Account;
import nl.abrouwer.extrack.domain.model.ImportStatus;
import nl.abrouwer.extrack.domain.model.Transaction;
import nl.abrouwer.extrack.domain.model.TransactionImport;
import nl.abrouwer.extrack.domain.model.TransactionSource;
import nl.abrouwer.extrack.domain.model.User;
import nl.abrouwer.extrack.domain.repo.TransactionImportRepository;
import nl.abrouwer.extrack.exception.ExpenseFileStorageException;

@Service
@Transactional
public class TransactionImportServiceImpl implements TransactionImportService
{
	Logger log = LoggerFactory.getLogger(TransactionImportServiceImpl.class);

	private final TransactionImportRepository transactionImportRepository;

	private final AccountService accountService;

	private final UserService userService;


	public TransactionImportServiceImpl(TransactionImportRepository transactionImportRepository,
			AccountService accountService, UserService userService)
	{
		this.transactionImportRepository = transactionImportRepository;
		this.accountService = accountService;
		this.userService = userService;
	}


	@Override
	public List<TransactionImport> getAllTransactionImportsForUser(String username)
	{
		User user = userService.getUser(username);

		List<TransactionImport> result = new ArrayList<>();
		if (user != null)
		{
			result = transactionImportRepository.findAllByUser(user);
		}

		return result;
	}


	@Override
	public void importTransactions(MultipartFile file)
	{
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.getUser(username);

		Assert.notNull(user, "A logged in user is required for uploading transaction files");

		TransactionImport transactionImport = new TransactionImport();

		try
		{
			if (file.isEmpty())
			{
				throw new ExpenseFileStorageException("Failed to store empty file " + file.getOriginalFilename());
			}

			// Check if file has already been uploaded
			String hash = getFileChecksum(file);
			if (transactionImportRepository.findByHash(hash) != null)
			{
				throw new ExpenseFileStorageException("This file has already been imported " + file.getOriginalFilename());
			}

			transactionImport.setUser(user);
			transactionImport.setFile(file.getOriginalFilename());
			transactionImport.setHash(hash);
			transactionImport.setStatus(ImportStatus.WAITING);

			File importFile = convertToFile(file);

			transactionImport.setStatus(ImportStatus.IN_PROGRESS);

			int skipLines = 1;

			try (CSVReader reader = new CSVReader(new FileReader(importFile), ',', '"'))
			{
				for (int i = 0; i < skipLines; i++)
				{
					reader.readNext();
				}

				String[] transactionLine;
				while ((transactionLine = reader.readNext()) != null)
				{
					Transaction transaction = new Transaction();

					Account account = accountService.findByUserAndIban(transactionImport.getUser(), transactionLine[0]);

					if (account == null)
					{
						throw new ExpenseFileStorageException("IBAN is not known, please create a new account first for iban " + transactionLine[0]);
					}

					Currency currency = account.getCurrency();
					DecimalFormatSymbols currencySymbols = new DecimalFormatSymbols();
					currencySymbols.setCurrency(currency);
					currencySymbols.setDecimalSeparator(',');

					DecimalFormat currencyFormat = new DecimalFormat();
					currencyFormat.setDecimalFormatSymbols(currencySymbols);
					currencyFormat.setNegativePrefix("-");
					currencyFormat.setPositivePrefix("+");
					currencyFormat.setParseBigDecimal(true);

					transaction.setAccount(account);
					transaction.setMutationDate(LocalDate.parse(transactionLine[4]));
					transaction.setInterestDate(LocalDate.parse(transactionLine[5]));
					transaction.setAmount((BigDecimal) currencyFormat.parse(transactionLine[6]));
					transaction.setBalanceAfterTransaction((BigDecimal) currencyFormat.parse(transactionLine[7]));
					transaction.setCounterPartyIban(transactionLine[8]);
					transaction.setCounterPartyName(transactionLine[9]);
					transaction.setCounterPartyBic(transactionLine[12]);
					transaction.setType(transactionLine[13]);
					transaction.setDescription(transactionLine[19]);
					transaction.setSource(TransactionSource.IMPORT);
					transaction.setTransactionImport(transactionImport);

					transactionImport.getTransactions().add(transaction);
				}

				transactionImport.setStatus(ImportStatus.FINISHED);
			}
			catch (Exception e)
			{
				transactionImport.setStatus(ImportStatus.ERROR);
				throw e;
			}

			transactionImportRepository.save(transactionImport);
		}
		catch (Exception e)
		{
			if (transactionImport != null)
			{
				transactionImport.setStatus(ImportStatus.ERROR);
			}
			throw new ExpenseFileStorageException("Failed to import file " + file.getOriginalFilename(), e);
		}
	}


	private String getFileChecksum(MultipartFile file) throws IOException, NoSuchAlgorithmException
	{
		MessageDigest digest = MessageDigest.getInstance("MD5");
		InputStream inputStream = file.getInputStream();

		// Create byte array to read data in chunks
		byte[] byteArray = new byte[1024];
		int bytesCount = 0;

		// Read file data and update in message digest
		while ((bytesCount = inputStream.read(byteArray)) != -1)
		{
			digest.update(byteArray, 0, bytesCount);
		}
		;

		// close the stream; We don't need it now.
		inputStream.close();

		// Get the hash's bytes
		byte[] bytes = digest.digest();

		// This bytes[] has bytes in decimal format;
		// Convert it to hexadecimal format
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++)
		{
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}

		// return complete hash
		return sb.toString();
	}

	private File convertToFile(MultipartFile file) throws IOException
	{
		File convertedFile = new File(file.getOriginalFilename());
		convertedFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convertedFile);
		fos.write(file.getBytes());
		fos.close();
		return convertedFile;
	}
}
