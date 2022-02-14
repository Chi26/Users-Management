package com.shopping.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("")
	public String viewHome() {
		return"index";
	}
	
	@GetMapping("/login")
	public String viewLogin() {
		return"login";
	}
}
