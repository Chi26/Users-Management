package com.shopping.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.hibernate.internal.build.AllowSysOut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopping.admin.repository.UserRepository;
import com.shopping.common.entity.Role;
import com.shopping.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TestEntityManager testEntityManager;

//	@Test
//	public void createOneUserManager() {
//		Role roleAdmin = testEntityManager.find(Role.class, 1);
//
//		User user1 = new User("aaa@gmail.com", "123456", "Peggy", "Chien");
//		user1.addRole(roleAdmin);
//		User saveUser1 = userRepository.save(user1);
//		assertThat(saveUser1.getId()).isGreaterThan(0);
//	}
//
//	@Test
//	public void testCreateNewUserTwoRoles() {
//		User userAndy = new User("andy@gmail.com", "andygo", "Andy", "Lee");
//		Role editor = new Role(3);
//		Role assistant = new Role(5);
//		userAndy.addRole(assistant);
//		userAndy.addRole(editor);
//		User saveUser = userRepository.save(userAndy);
//		assertThat(saveUser.getId()).isGreaterThan(0);
//	}
	
	@Test
	public void findAll() {
	Iterable<User> listUsers= userRepository.findAll();
	listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void findbyId() {
	User user= userRepository.findById(3).get();
	System.out.println(user);
	assertThat(user).isNotNull();
	}
	
	@Test
	public void updateUserDetail() {
	User userPeggy= userRepository.findById(1).get();
	userPeggy.setEnabled(true);
	userPeggy.setLastName("Wu");
	userRepository.save(userPeggy);
	}

	@Test
	public void testUpdateUserRole() {
		User userAndy = userRepository.findById(3).get();
		Role salesperson = new Role(3);
		Role shipper= new Role(5);
		Role assistant= new Role(6);
		//userAndy.getRoles().remove(salesperson);
		//userAndy.getRoles().remove(shipper);
		userAndy.addRole(salesperson);
		userAndy.addRole(assistant);
		userRepository.save(userAndy);
	}
	
	@Test
	public void getUserByEmail() {
		String email = "aaa@gmail.com";
		User userByEmail = userRepository.findByEmail(email);
		System.out.println(userByEmail.getFirstName());
		assertThat(userByEmail).isNotNull();
	}
}
