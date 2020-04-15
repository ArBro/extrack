package nl.abrouwer.extrack.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import nl.abrouwer.extrack.domain.model.Account;
import nl.abrouwer.extrack.domain.model.TransactionCategory;
import nl.abrouwer.extrack.service.AccountService;
import nl.abrouwer.extrack.service.TransactionService;
import nl.abrouwer.extrack.web.beans.AccountCreateBean;
import nl.abrouwer.extrack.web.beans.CategoryCreateBean;

@Controller
@RequestMapping("/settings")
public class SettingsController extends BaseController
{
	private final AccountService accountService;

	private final TransactionService transactionService;


	public SettingsController(AccountService accountService, TransactionService transactionService)
	{
		this.accountService = accountService;
		this.transactionService = transactionService;
	}


	@GetMapping("/accounts")
	public String accountsOverview(Model model)
	{
		List<Account> accounts = accountService.findAll();

		model.addAttribute("accounts", accounts);

		return "settings/accounts-overview";
	}


	@GetMapping("/accounts/add")
	public String addAccount(Model model, AccountCreateBean accountCreateBean)
	{
		accountCreateBean = new AccountCreateBean();
		return "fragment/settings/accounts-add :: addAccountModal";
	}


	@GetMapping("/categories")
	public String categoriesOverview(Model model)
	{
		List<TransactionCategory> categories = transactionService.getAllTransactionCategories();

		model.addAttribute("categories", categories);

		return "settings/categories-overview";
	}


	@GetMapping("/categories/add")
	public String addCategory(Model model, CategoryCreateBean categoryCreateBean)
	{
		categoryCreateBean = new CategoryCreateBean();
		return "fragment/settings/categories-add :: addCategoryModal";
	}
}
