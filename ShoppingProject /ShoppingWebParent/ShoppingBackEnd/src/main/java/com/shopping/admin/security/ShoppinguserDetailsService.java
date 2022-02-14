package com.shopping.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shopping.admin.repository.UserRepository;
import com.shopping.common.entity.User;

public class ShoppinguserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if (user !=null) {
			return new ShoppingUserDetails(user);
		}
		
		throw new UsernameNotFoundException("Could not find the email");
	}

}
