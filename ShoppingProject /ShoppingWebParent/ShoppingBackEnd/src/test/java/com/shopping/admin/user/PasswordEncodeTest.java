package com.shopping.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncodeTest {

	@Test
	public void encodePassword() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String rawPassword = "123456789";
		String encode = bCryptPasswordEncoder.encode(rawPassword);
		System.out.println(encode);
		boolean matches = bCryptPasswordEncoder.matches(rawPassword, encode);
		assertThat(matches).isTrue();
	}
}
