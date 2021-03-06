package com.shopping.admin.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopping.admin.FileUploadUtil;
import com.shopping.admin.user.UserCSVExporter;
import com.shopping.admin.user.UserNotFoundException;
import com.shopping.admin.user.UserService;
import com.shopping.common.entity.Role;
import com.shopping.common.entity.User;


@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	
	@GetMapping("/users")
	public String getAllUsers(Model model) {
	 return	processQueryAllByPageAction(1,model, null);
	}
	
	@GetMapping("/users/create")
	public String createUser(Model model) {
		  List<Role> allRoles = userService.getAllRoles();
		User user = new User();
		user.setEnabled(true);
		model.addAttribute("user",user);
		model.addAttribute("allRoles",allRoles);
		model.addAttribute("pageTitle","Create New User");
		return"Users/usersForm";
	}
	
	@PostMapping("/users/save")
	public String saveUser( User user, RedirectAttributes redirectAttributes,@RequestParam("image") MultipartFile multipartFile,HttpServletRequest request) throws IOException {
		if (!multipartFile.isEmpty()) {
			String fileName =multipartFile.getOriginalFilename();
			user.setPhotos(fileName);
			 userService.save(user);
			
			String uploadDir = "user-photos";
            
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			
	
		}else {
			if (user.getPhotos().isEmpty()) user.setPhotos(null);
			userService.save(user);
		}
		redirectAttributes.addFlashAttribute("message", "Saved Successfully !");
		return"redirect:/users";
	}
	
	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes,Model model){
	  try {
		  User user= userService.get(id);
		  List<Role> allRoles = userService.getAllRoles();
		  model.addAttribute("user",user);
		  model.addAttribute("pageTitle","Edit User (ID : "+id+")");
		  model.addAttribute("allRoles",allRoles);
		  return"Users/usersForm";
	} catch (UserNotFoundException ex) {
		redirectAttributes.addFlashAttribute("message",ex.getMessage());
		return"redirect:/users";
	}
		
		
	}
	
	@GetMapping("/users/delete/{id}")
	public String deleteById(@PathVariable(name = "id") Integer id,RedirectAttributes redirectAttributes) {
		userService.deleteById(id);
		redirectAttributes.addFlashAttribute("message","Id: "+ id +"  has been deleted successfully !");
		return"redirect:/users";
	}
	
	@GetMapping("/users/{id}/enabled/true")
	public String updateEnableTrue(@PathVariable(name = "id") Integer id,RedirectAttributes redirectAttributes) {
		userService.updateEnabled(id, true);
		redirectAttributes.addFlashAttribute("message","The enabled status has been updated successfully !");
		return"redirect:/users";
	}
	
	@GetMapping("/users/{id}/enabled/false")
	public String updateEnableFalse(@PathVariable(name = "id") Integer id,RedirectAttributes redirectAttributes) {
		userService.updateEnabled(id, false);
		redirectAttributes.addFlashAttribute("message","The enabled status has been updated successfully !");
		return"redirect:/users";
	}
	
	@GetMapping("/users/page/{pageNum}")
	public String processQueryAllByPageAction(@PathVariable("pageNum") int pageNum, Model m,@Param("keyword")String keyword) {
		
		
		Page<User> page =userService.findByPage(keyword,pageNum);
         List<User> allUsers = page.getContent();
		System.out.println(page.getTotalElements());
		System.out.println(page.getTotalPages());
		
		m.addAttribute("totalPages", page.getTotalPages());
		m.addAttribute("totalElements", page.getTotalElements());
		m.addAttribute("allUsers", allUsers);
		m.addAttribute("currentPage", pageNum);
		m.addAttribute("keyword", keyword);
		return "Users/users";
	}
	
	@GetMapping("/users/export/csv")
	public void exportToCSV(HttpServletResponse response) throws IOException {
		List<User> allUsers = userService.getAll();
		UserCSVExporter exporter=  new UserCSVExporter();
		exporter.export(allUsers, response);
	}
	

}
