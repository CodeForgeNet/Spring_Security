package com.cfn.main.service;

import java.util.Optional;

import com.cfn.main.model.MyUser;

public interface UserService {
	
	Optional<MyUser> findByUserName(String username); // Spring data jpa automatically implements this method to find a user by username.
}
