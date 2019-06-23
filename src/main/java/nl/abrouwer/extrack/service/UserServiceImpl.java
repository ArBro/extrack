package nl.abrouwer.extrack.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.abrouwer.extrack.domain.model.User;
import nl.abrouwer.extrack.domain.repo.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService
{
	private final UserRepository userRepository;


	public UserServiceImpl(UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}


	@Override
	public User getUser(String username)
	{
		return userRepository.findByUsername(username);
	}

}
