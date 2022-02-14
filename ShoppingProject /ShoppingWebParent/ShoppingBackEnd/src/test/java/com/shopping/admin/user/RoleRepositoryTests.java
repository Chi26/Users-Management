package com.shopping.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopping.admin.repository.RoleRepository;
import com.shopping.common.entity.Role;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {

	@Autowired
	private RoleRepository roleRepository;
	
	@Test
	public void testCreateFirstRole() {
	   Role role = new Role("Admin","Manage everything");
	  Role saveRole = roleRepository.save(role);
	  assertThat(saveRole.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateFourRole() {
	   Role sales = new Role("Salesperson","Manage product price,customers,shipping,orders,sales report");
	   Role editor = new Role("Editor","Manage category,brands,products,articles,menus");
	   Role shipper = new Role("Shipper","view product,view order,update oreder status");
	   Role assistant= new Role("Asistant","Manage questions and reviews");
	   roleRepository.saveAll(List.of(sales,editor,shipper,assistant));
	}
	
	
}
