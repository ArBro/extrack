package nl.abrouwer.extrack.security;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.abrouwer.extrack.domain.model.User;
import nl.abrouwer.extrack.domain.repo.UserRepository;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService
{
	private final UserRepository userRepository;


	public UserDetailsServiceImpl(UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		User user = userRepository.findByUsername(username);
		if (user == null)
		{
			throw new UsernameNotFoundException(username);
		}

		Set<GrantedAuthority> authorities = new TreeSet<GrantedAuthority>(new AuthorityComparator());

		// if (user.getRoles() != null)
		// {
		// for (Role role : user.getRoles())
		// {
		// authorities.add(new SimpleGrantedAuthority("ROLE_" +
		// role.getRole()));
		// }
		// }

		UserDetails userDetails = new UserDetails(
				user.getId(),
				user.getUsername(),
				user.getPassword(),
				checkUserEnabled(user), // user.isEnabled(),
				authorities);

		return userDetails;
	}


	private boolean checkUserEnabled(User user)
	{
		// Implement method. Set Webservice role users as not enabled.
		return true;
	}


	private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable
	{
		private static final long serialVersionUID = 1L;


		@Override
		public int compare(GrantedAuthority g1, GrantedAuthority g2)
		{
			// Neither should ever be null as each entry is checked before
			// adding it to the set.
			// If the authority is null, it is a custom authority and should
			// precede others.

			if (g2.getAuthority() == null)
			{
				return -1;
			}

			if (g1.getAuthority() == null)
			{
				return 1;
			}

			return g1.getAuthority().compareTo(g2.getAuthority());
		}
	}
}