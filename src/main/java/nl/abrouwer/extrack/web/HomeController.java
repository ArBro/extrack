package nl.abrouwer.extrack.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController extends BaseController
{
	@GetMapping("/index")
	public String index()
	{
		return "index";
	}
}
