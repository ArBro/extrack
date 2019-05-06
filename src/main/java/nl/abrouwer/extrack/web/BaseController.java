package nl.abrouwer.extrack.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class BaseController
{
	@GetMapping(value = "")
	public String underConstruction()
	{
		return "index";
	}
}
