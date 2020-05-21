package assignment6master.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import assignment6master.models.User;
import assignment6master.repositories.UsersRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	UsersRepo usersRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = usersRepository.findByUserName(username);
		
		user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
		
		return user.map(MyUserDetails::new).get();
	}

	
	
}
