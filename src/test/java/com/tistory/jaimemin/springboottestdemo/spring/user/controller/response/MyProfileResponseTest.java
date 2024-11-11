package com.tistory.jaimemin.springboottestdemo.spring.user.controller.response;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.tistory.jaimemin.springboottestdemo.spring.user.domain.User;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.UserStatus;

class MyProfileResponseTest {

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
		MyProfileResponse myProfileResponse = MyProfileResponse.from(user);

		// then
		assertThat(myProfileResponse.getId()).isEqualTo(1L);
		assertThat(myProfileResponse.getEmail()).isEqualTo("aaa@gmail.com");
		assertThat(myProfileResponse.getNickname()).isEqualTo("aaa");
		assertThat(myProfileResponse.getAddress()).isEqualTo("Seoul");
		assertThat(myProfileResponse.getStatus()).isEqualTo(UserStatus.ACTIVE);
		assertThat(myProfileResponse.getLastLoginAt()).isEqualTo(100L);
	}
}