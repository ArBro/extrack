package nl.abrouwer.extrack.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails
{
	private static final long serialVersionUID = 1L;

	private final Long userId;
	private final String username;
	private final String password;
	private final boolean enabled;
	private final Collection<GrantedAuthority> authorities;
	
	public UserDetails(long userId, String username, String password, boolean enabled, Collection<GrantedAuthority> authorities)
	{
		if((username == null) || "".equals(username))
		{
			throw new IllegalArgumentException("Cannot pass null or empty value for username to constructor");
		}
		if((password == null) || "".equals(password))
		{
			throw new IllegalArgumentException("Cannot pass null or empty value for password to constructor");
		}
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
	}
	


	public long getUserId()
	{
		return userId.longValue();
	}


	@Override
	public String getUsername()
	{
		return username;
	}
	
	
	@Override
	public String getPassword()
	{
		return password;
	}


	@Override
	public boolean isEnabled()
	{
		return enabled;
	}


	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}


	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}


	@Override
	public Collection<GrantedAuthority> getAuthorities()
	{
		return authorities;
	}


	@Override
	public int hashCode()
	{
		int code = 9792;

		for(GrantedAuthority authority : getAuthorities())
		{
			code = code * (authority.hashCode() % 7);
		}

		if(this.getPassword() != null)
		{
			code = code * (this.getPassword().hashCode() % 7);
		}

		if(this.getUsername() != null)
		{
			code = code * (this.getUsername().hashCode() % 7);
		}
		
		if(this.isEnabled())
		{
			code = code * -7;
		}

		return code;
	}


	private static SortedSet<GrantedAuthority> sortAuthorities(
			Collection<GrantedAuthority> authorities)
	{
		Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
		// Ensure array iteration order is predictable (as per
		// UserDetails.getAuthorities() contract and SEC-717)
		SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<GrantedAuthority>(
				new AuthorityComparator());

		for(GrantedAuthority grantedAuthority : authorities)
		{
			Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
			sortedAuthorities.add(grantedAuthority);
		}

		return sortedAuthorities;
	}


	private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable
	{
		private static final long serialVersionUID = 1L;

		@Override
		public int compare(GrantedAuthority g1, GrantedAuthority g2)
		{
			// Neither should ever be null as each entry is checked before adding it
			// to the set.
			// If the authority is null, it is a custom authority and should precede
			// others.
			if(g2.getAuthority() == null)
			{
				return -1;
			}

			if(g1.getAuthority() == null)
			{
				return 1;
			}

			return g1.getAuthority().compareTo(g2.getAuthority());
		}
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append(": ");
		sb.append("UserId: ").append(this.userId).append("; ");
		sb.append("Username: ").append(this.username).append("; ");
		sb.append("Password: [PROTECTED]; ");
		sb.append("Enabled: ").append(this.enabled).append("; ");

		if(!authorities.isEmpty())
		{
			sb.append("Granted Authorities: ");

			boolean first = true;
			for(GrantedAuthority auth : authorities)
			{
				if(!first)
				{
					sb.append(",");
				}
				first = false;

				sb.append(auth);
			}
		}
		else
		{
			sb.append("Not granted any authorities");
		}

		return sb.toString();
	}
}
