package nl.abrouwer.extrack.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired
	private UserDetailsService userDetailsService;


	@Override
	public void configure(WebSecurity web) throws Exception
	{
		// @formatter:off
		web.ignoring()
			.antMatchers("/favicon.ico")
			.antMatchers("/images/**")
			.antMatchers("/css/**")
			.antMatchers("/js/**")
			.antMatchers("/webjars/**")
			.antMatchers("/resources/**")
			.antMatchers("/usercheck/**")
		;
		//debug(true);
		// @formatter:on
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.headers().frameOptions().sameOrigin();

		// @formatter:off
		http
			.csrf().disable()
			
			.authorizeRequests()
				.anyRequest().authenticated() 
			.and()
			.formLogin()
				.permitAll()
				.loginPage("/login")
				.failureUrl("/login?error=1")
				.defaultSuccessUrl("/index", true)	
				
			.and()
			.logout()
				// allow GET /logout
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login")
			
			.and()
			.exceptionHandling().accessDeniedPage("/access?accessdenied=1")
			
			;
		// @formatter:on
	}
}
