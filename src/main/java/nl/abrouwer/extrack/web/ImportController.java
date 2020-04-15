package nl.abrouwer.extrack.web;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
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

import nl.abrouwer.extrack.exception.ExpenseFileNotFoundException;
import nl.abrouwer.extrack.service.TransactionImportService;
import nl.abrouwer.extrack.service.TransactionService;
import nl.abrouwer.extrack.service.UserService;

@Controller
@RequestMapping("/imports")
public class ImportController extends BaseController
{
	private final TransactionImportService transactionImportService;

	private final MessageSource messageSource;


	public ImportController(UserService userService, TransactionService transactionService, TransactionImportService transactionImportService,
			MessageSource messageSource)
	{
		this.transactionImportService = transactionImportService;
		this.messageSource = messageSource;
	}


	@GetMapping("/overview")
	public String transactionImport(Model model)
	{
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("imports", transactionImportService.getAllTransactionImportsForUser(username));

		return "import/overview";
	}


	@PostMapping("/add")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes)
	{
		try
		{
			transactionImportService.importTransactions(file);
			log.info("TransactionImport {} successfully uploaded", file.getOriginalFilename());
			redirectAttributes.addFlashAttribute("message",
					messageSource.getMessage("import.upload.success", new String[] { file.getOriginalFilename() }, new Locale("nl", "NL")));

		}
		catch (Exception e)
		{
			log.error("Error occurred while uploading transactionImport {} with error {}", file.getOriginalFilename(), e, e);
			redirectAttributes.addFlashAttribute("error",
					messageSource.getMessage("import.upload.error", new String[] { file.getOriginalFilename() }, new Locale("nl", "NL")));
		}

		return "redirect:/imports/overview";
	}


	@ExceptionHandler(ExpenseFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(ExpenseFileNotFoundException exc)
	{
		return ResponseEntity.notFound().build();
	}
}
