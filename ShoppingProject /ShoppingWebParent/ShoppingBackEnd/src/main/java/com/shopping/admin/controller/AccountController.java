package com.shopping.admin.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopping.admin.FileUploadUtil;
import com.shopping.admin.security.ShoppingUserDetails;
import com.shopping.admin.user.UserService;
import com.shopping.common.entity.User;

@Controller
public class AccountController {

	
	@Autowired
	private UserService userService;
	
	@GetMapping("/account")
	public String viewDetails(@AuthenticationPrincipal ShoppingUserDetails loggedUser, Model model) {
		String email = loggedUser.getUsername();
		User user = userService.findByEmail(email);
		model.addAttribute("user",user);
		return "Users/accountForm";
	}
	@PostMapping("/account/update")
	public String saveDetails( User user, RedirectAttributes redirectAttributes,@RequestParam("image") MultipartFile multipartFile,HttpServletRequest request) throws IOException {
		if (!multipartFile.isEmpty()) {
			String fileName =multipartFile.getOriginalFilename();
			user.setPhotos(fileName);
		    userService.updateUser(user);
			
			String uploadDir = "user-photos";

            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			
	
		}else {
			if (user.getPhotos().isEmpty()) user.setPhotos(null);
			userService.updateUser(user);
		}
		
	
		
		redirectAttributes.addFlashAttribute("message", "Updated Successfully !");
		return"redirect:/account";
	}
}
