package nl.abrouwer.extrack.web;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import nl.abrouwer.extrack.domain.model.Transaction;
import nl.abrouwer.extrack.domain.model.TransactionCategory;
import nl.abrouwer.extrack.service.TransactionService;
import nl.abrouwer.extrack.web.beans.TransactionBean;

@Controller
@RequestMapping("/transactions")
public class TransactionsController extends BaseController
{
	private final TransactionService transactionService;


	public TransactionsController(TransactionService transactionService)
	{
		this.transactionService = transactionService;
	}


	@GetMapping("/overview")
	public String transactionOverview(Model model, @PageableDefault(size = Integer.MAX_VALUE, sort = "id", direction = Direction.ASC) Pageable pageable)
	{
		Page<Transaction> transactions = transactionService.getAllTransactions(pageable);
		List<TransactionCategory> categories = transactionService.getAllTransactionCategories();

		model.addAttribute("transactionsBean", new TransactionBean(transactions.getContent()));
		model.addAttribute("categories", categories);

		return "transactions/overview";
	}
}
