package com.cfn.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cfn.main.model.MyUser;
import com.cfn.main.repositories.MyUserRepo;

@RestController
public class RegistrationCont {
	
	@Autowired
	private MyUserRepo myUserRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/register/user")
	public MyUser createUser( @RequestBody MyUser user ) {
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return myUserRepo.save(user);
	}
	
}
