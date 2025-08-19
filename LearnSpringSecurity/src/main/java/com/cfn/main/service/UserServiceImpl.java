package com.cfn.main.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cfn.main.model.MyUser;
import com.cfn.main.repositories.MyUserRepo;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private MyUserRepo myUserRepo;

//	@Override
//	public Optional<MyUser> findByUserName(String username) {
//
//		if (username == null || username.isEmpty()) {
//			return Optional.empty(); // Return empty if username is null or empty
//		}
//		return myUserRepo.findByUserNameIgnoreCase(username); // Fetch user by username ignoring case
//		
//	}

	@Override
	public Optional<MyUser> findByUserName(String username) {
		return myUserRepo.findByUserNameIgnoreCase(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<MyUser> user = findByUserName(username);
		if (user.isPresent()) {
			var userObj = user.get();
			return User.builder().username(userObj.getUserName()).password(userObj.getPassword())
					.roles(getRoles(userObj)).build();
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}

	}

	private String[] getRoles(MyUser user) {
		if (user.getRole() == null || user.getRole().isEmpty()) {
			return new String[] { "USER" }; // Default role if none is specified
		}
		return user.getRole().split(","); // Assuming roles are comma-separated
	}

}
