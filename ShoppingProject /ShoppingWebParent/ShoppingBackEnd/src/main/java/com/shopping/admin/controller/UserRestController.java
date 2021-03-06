package com.shopping.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.admin.user.UserService;

@RestController
public class UserRestController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/users/checkEmail")
	public String checkDuplicateEmail(@Param(value = "id") Integer id,@Param(value = "email") String email) {
		System.out.println(email);
		return userService.isEmailUnique(id,email) ? "ok" : "Duplicated";
	}
}
