package com.tistory.jaimemin.springboottestdemo.spring.user.controller.response;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.tistory.jaimemin.springboottestdemo.spring.user.domain.User;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.UserStatus;

public class UserResponseTest {

	@Test
	public void User로_응답을_생성할_수_있다() {
		// given
		User user = User.builder()
			.id(1L)
			.email("aaa@gmail.com")
			.nickname("aaa")
			.address("Seoul")
			.status(UserStatus.ACTIVE)
			.lastLoginAt(100L)
			.build();

		// when
		UserResponse userResponse = UserResponse.from(user);

		// then
		assertThat(userResponse.getId()).isEqualTo(1L);
		assertThat(userResponse.getEmail()).isEqualTo("aaa@gmail.com");
		assertThat(userResponse.getNickname()).isEqualTo("aaa");
		assertThat(userResponse.getStatus()).isEqualTo(UserStatus.ACTIVE);
		assertThat(userResponse.getLastLoginAt()).isEqualTo(100L);
	}
}
