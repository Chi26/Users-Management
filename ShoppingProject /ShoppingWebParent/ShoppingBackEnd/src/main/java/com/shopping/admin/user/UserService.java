package com.shopping.admin.user;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopping.admin.repository.RoleRepository;
import com.shopping.admin.repository.UserRepository;
import com.shopping.common.entity.Role;
import com.shopping.common.entity.User;

@Service
@Transactional
public class UserService {

	public static final int USERS_PER_PAGE = 4;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<User> getAll() {
		return (List<User>) userRepository.findAll(Sort.by("firstName").ascending());
	}
	
	public List<Role> getAllRoles() {
		return (List<Role>) roleRepository.findAll();
	}

	public void save(User user) {
		boolean isUpdatingUser = (user.getId() !=null);
		
		if (isUpdatingUser) {
			User existingUser = userRepository.findById(user.getId()).get();
			if (user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			} else {
				encodePassword(user);
			}
		}else {
			encodePassword(user);
		}
		
	   userRepository.save(user);
	}
	
	private void encodePassword(User user) {
		String encode = passwordEncoder.encode(user.getPassword());
		user.setPassword(encode);
	}
//	public boolean isEmailUnique(String email) {
//		User userByEmail = userRepository.getUserByEmail(email);
//		System.out.println(userByEmail);
//		return userByEmail == null;
//	}
	
	public boolean isEmailUnique(Integer id,String email) {
		User findByEmail = userRepository.findByEmail(email);
		if (findByEmail == null) return true;
			boolean isCreateNewUser = (id == null);
			if (isCreateNewUser) {
				if(findByEmail !=null) return false;
			}else {
				if (findByEmail.getId() != id) {
					return false;
				}
			}
		return true;
		
	}

	public User get(Integer id) throws UserNotFoundException {
		try {
			return userRepository.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new UserNotFoundException("Could not found any user with ID: "+id);
		}
		
	}
	
	public void deleteById(Integer id) {
		userRepository.deleteById(id);
	}

	public void updateEnabled(Integer id, boolean enabled) {
		userRepository.updateEnabled(id, enabled);
	}
	
	public Page<User> findByPage(String keyword ,int pageNum) {
		Pageable pageable = PageRequest.of(pageNum -1, USERS_PER_PAGE);
		if (keyword !=null) {
			return userRepository.findAll(keyword,pageable);
		}
		
		return userRepository.findAll(pageable);
	}
	
	public User updateUser(User userFromForm) {
		User userFromDB = userRepository.findById(userFromForm.getId()).get();
		
		if (!userFromForm.getPassword().isEmpty()) {
			userFromDB.setPassword(userFromForm.getPassword());
			encodePassword(userFromDB);
		}
		
		if (userFromForm.getPhotos() != null) {
			userFromDB.setPhotos(userFromForm.getPhotos());
		}
		
		userFromDB.setFirstName(userFromForm.getFirstName());
		userFromDB.setLastName(userFromForm.getLastName());
		
		return userRepository.save(userFromDB);
	}
	
	public User findByEmail(String email) {
	  return userRepository.findByEmail(email);
	}
	
}
