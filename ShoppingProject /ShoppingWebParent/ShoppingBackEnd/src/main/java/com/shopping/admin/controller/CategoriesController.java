package com.shopping.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.shopping.admin.user.CategoriesService;
import com.shopping.common.entity.Category;
import com.shopping.common.entity.User;

@Controller
public class CategoriesController {

	@Autowired
	private CategoriesService categoriesService;
	
	@GetMapping("/categories")
	public String getAllUsers(Model model) {
	 return	processQueryAllByPageAction(1, model);
	}
	
	@GetMapping("/categories/page/{pageNum}")
	public String processQueryAllByPageAction(@PathVariable("pageNum") int pageNum, Model m) {
		
		
		Page<Category> page =categoriesService.findByPage(pageNum);
         List<Category> allCategories = page.getContent();
		System.out.println(page.getTotalElements());
		System.out.println(page.getTotalPages());
		
		m.addAttribute("totalPages", page.getTotalPages());
		m.addAttribute("totalElements", page.getTotalElements());
		m.addAttribute("allCategories", allCategories);
		m.addAttribute("currentPage", pageNum);
		
		return "Categories/Categories";
	}
}
