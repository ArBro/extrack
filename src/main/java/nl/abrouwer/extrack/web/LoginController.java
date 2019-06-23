package nl.abrouwer.extrack.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController extends BaseController
{
	@GetMapping("/login")
	public String login()
	{
		return "login";
	}
}
