package nl.abrouwer.extrack.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Profile("default")
@Configuration
@Order(1)
public class CustomH2ConsoleSecurityConfig extends WebSecurityConfigurerAdapter
{

	@Autowired
	private H2ConsoleProperties console;


	@Override
	public void configure(HttpSecurity http) throws Exception
	{
		String path = this.console.getPath();
		String antPattern = (path.endsWith("/") ? path + "**" : path + "/**");

		HttpSecurity h2Console = http.antMatcher(antPattern);

		h2Console.csrf().disable();
		h2Console.httpBasic();
		h2Console.headers().frameOptions().sameOrigin();

		http.authorizeRequests().anyRequest().permitAll();
	}

}