package nl.abrouwer.extrack.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import nl.abrouwer.extrack.exception.ExpenseFileNotFoundException;
import nl.abrouwer.extrack.service.StorageService;
import nl.abrouwer.extrack.service.TransactionImportService;

@Controller
public class TransactionImportController extends BaseController
{
	private final StorageService storageService;

	private final TransactionImportService transactionImportService;


	@Autowired
	public TransactionImportController(StorageService storageService, TransactionImportService transactionImportService)
	{
		this.storageService = storageService;
		this.transactionImportService = transactionImportService;
	}


	@GetMapping("/transactions/import")
	public String index(Model model)
	{
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("imports", transactionImportService.getAllTransactionImportsForUser(username));

		return "transactions/import";
	}


	@PostMapping("/transactions/import")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes)
	{
		try
		{
			transactionImportService.importTransactions(file);
			redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
		}
		catch (Exception e)
		{
			log.error("Error occurred while uploading {} with error {}", file.getOriginalFilename(), e, e);
			redirectAttributes.addFlashAttribute("error", "Error while uploading " + file.getOriginalFilename() + "!");
		}

		return "redirect:/transactions/import";
	}


	@ExceptionHandler(ExpenseFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(ExpenseFileNotFoundException exc)
	{
		return ResponseEntity.notFound().build();
	}
}
