package com.shopping.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.shopping.common.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {


	 public User findByEmail(@Param(value = "email")  String email);
	 
	 @Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
	 @Modifying
	 public void updateEnabled(Integer id,boolean enabled);
		
	 @Query("SELECT u from User u WHERE CONCAT( u.firstName,' ', u.lastName , ' ' ,u.email) LIKE %?1% ")
	public Page<User> findAll(String keyword ,Pageable pageable);
	
}
