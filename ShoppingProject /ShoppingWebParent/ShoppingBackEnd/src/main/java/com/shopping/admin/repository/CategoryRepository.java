package com.shopping.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.shopping.common.entity.Category;
import com.shopping.common.entity.User;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

	public Page<Category> findAll(Pageable pageable);
}
