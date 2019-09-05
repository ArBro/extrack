package nl.abrouwer.extrack.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import nl.abrouwer.extrack.domain.model.User;
import nl.abrouwer.extrack.exception.ExpenseFileNotFoundException;
import nl.abrouwer.extrack.service.TransactionImportService;
import nl.abrouwer.extrack.service.TransactionService;
import nl.abrouwer.extrack.service.UserService;

@Controller
@RequestMapping("/transactions")
public class TransactionsController extends BaseController
{
	private final UserService userService;

	private final TransactionService transactionService;

	private final TransactionImportService transactionImportService;


	@Autowired
	public TransactionsController(UserService userService, TransactionService transactionService, TransactionImportService transactionImportService)
	{
		this.userService = userService;
		this.transactionService = transactionService;
		this.transactionImportService = transactionImportService;
	}


	@GetMapping("/overview")
	public String transactionOverview(Model model,
			@PageableDefault(size = Integer.MAX_VALUE, sort = "transaction.id", direction = Direction.ASC) Pageable pageable)
	{
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.getUser(username);

		if (user == null)
		{
			throw new AuthenticationCredentialsNotFoundException("User not known");
		}

		model.addAttribute("transactions", transactionService.getAllTransactionsForUser(pageable, user));

		return "transactions/overview";
	}


	@GetMapping("/import")
	public String transactionImport(Model model)
	{
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("imports", transactionImportService.getAllTransactionImportsForUser(username));

		return "transactions/import";
	}


	@PostMapping("/import")
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
