package com.shopping.admin.user;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shopping.admin.repository.CategoryRepository;
import com.shopping.common.entity.Category;
import com.shopping.common.entity.User;


@Service
@Transactional
public class CategoriesService {

	public static final int CATEGORIES_PER_PAGE = 4;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Category> getAllRoles() {
		return (List<Category>) categoryRepository.findAll();
	}
	
	public Page<Category> findByPage(int pageNum) {
		Pageable pageable = PageRequest.of(pageNum -1, CATEGORIES_PER_PAGE);
		
		return categoryRepository.findAll(pageable);
	}
}
