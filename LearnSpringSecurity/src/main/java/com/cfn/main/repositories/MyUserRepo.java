package com.cfn.main.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cfn.main.model.MyUser;

public interface MyUserRepo extends JpaRepository<MyUser, Long> {
	
	Optional<MyUser> findByUserNameIgnoreCase(String userName);
	
}
