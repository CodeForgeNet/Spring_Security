package com.cfn.main.config; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // Used to configure web security
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // Enables Spring Security for the application
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User; // Utility for building user details
import org.springframework.security.core.userdetails.UserDetails; // Represents a user in Spring Security
import org.springframework.security.core.userdetails.UserDetailsService; // Service for loading user-specific data
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Password encoder using BCrypt hashing
import org.springframework.security.crypto.password.PasswordEncoder; // Interface for password encoding
import org.springframework.security.provisioning.InMemoryUserDetailsManager; // Stores user details in memory
import org.springframework.security.web.SecurityFilterChain; // Represents the security filter chain

@Configuration
@EnableWebSecurity // Enables web security in the application
public class SecurityConfiguration {
	
	@Autowired
	private UserDetailsService userDetailsService; // UserDetailsService to load user-specific data

	
	
	
//	1.
	@Bean 
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception { // Configures the security filter chain
		return httpSecurity
				.csrf(AbstractHttpConfigurer::disable) //after applying /register/** for all do this, disable it
				.authorizeHttpRequests(registry -> { // Sets up authorization rules for HTTP requests
					registry.requestMatchers("/home","/register/**").permitAll(); // Allows everyone to access /home
					registry.requestMatchers("/admin/**").hasRole("ADMIN"); // Only users with ADMIN role can access /admin/**
					registry.requestMatchers("/user/**").hasRole("USER"); // Only users with USER role can access /user/**
					registry.anyRequest().authenticated(); // All other requests require authentication
		})
//				.formLogin(AbstractAuthenticationFilterConfigurer::permitAll) //This login should be accessible by everyone
				// after creating custom login 
				.formLogin(httpSecurityFormLoginConfigurer -> {
					httpSecurityFormLoginConfigurer
					.loginPage("/login")
					.successHandler(new AuthenticationSuccessHandler())
					.permitAll();
				})
				.build(); // Builds the security filter chain
	}
	
	
	
	
	
	
	// 2.** Uncomment the following method to use in-memory user details, this method is not using database. note: that this is not recommended for production use
	
	
//	@Bean
//	public UserDetailsService userDetailsService() { // Provides user details for authentication
//		UserDetails normalUser = User.builder() // Builds a user with USER role
//				.username("karan") // Username is 'karan'
//				.password("$2a$12$KlZtDze0q585jy83K.8wS.WoXmReqttpkfzDCeKlu2j.aVo8UKwQG") // BCrypt-hashed password
//				.roles("USER") // Assigns USER role
//				.build(); // Builds the user details object
//		UserDetails adminUser = User.builder() // Builds a user with ADMIN and USER roles
//				.username("adminK") // Username is 'adminK'
//				.password("$2a$12$2nUm/HvApu2cflF0B93hHuFb9fkjEVwl2hEstiUu8epxKgSFePApy") // BCrypt-hashed password
//				.roles("ADMIN","USER") // Assigns ADMIN and USER roles
//				.build(); // Builds the user details object
//		
//		return new InMemoryUserDetailsManager(normalUser, adminUser); // Stores both users in memory for authentication
//	}
	
	
	
	
	
	//4.* method to use database for user details, this method is using database.
	@Bean
	public UserDetailsService userDetailService() {
		return userDetailsService; // Returns the UserDetailsService bean to be used by Spring Security
	}
	
	
//	5.
	@Bean
	public AuthenticationProvider authenticationProvider() { // Provides an authentication provider bean
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); 
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	
	
//	3.
	@Bean 
	public PasswordEncoder passwordEncoder() { // Provides a password encoder bean
		return new BCryptPasswordEncoder(); // Uses BCrypt for password encoding
	}

}