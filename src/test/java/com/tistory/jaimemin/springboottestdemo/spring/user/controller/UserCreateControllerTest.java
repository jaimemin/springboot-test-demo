package com.tistory.jaimemin.springboottestdemo.spring.user.controller;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.tistory.jaimemin.springboottestdemo.spring.common.service.port.UuidHolder;
import com.tistory.jaimemin.springboottestdemo.spring.mock.TestContainer;
import com.tistory.jaimemin.springboottestdemo.spring.user.controller.response.UserResponse;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.UserCreate;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.UserStatus;

class UserCreateControllerTest {

	@Test
	void 사용자는_회원_가입을_할_수있고_회원가입된_사용자는_PENDING_상태이다() throws Exception {
		// given
		TestContainer testContainer = TestContainer.builder()
			.uuidHolder(new UuidHolder() {
				@Override
				public String random() {
					return "aaaaaaaa-aaaaaaaa-aaaaaaa";
				}
			})
			.build();
		UserCreate userCreate = UserCreate.builder()
			.email("ccc@gmail.com")
			.nickname("ccc")
			.address("Pangyo")
			.build();

		// when
		ResponseEntity<UserResponse> result = testContainer.userCreateController.createUser(userCreate);

		// then
		assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
		assertThat(result.getBody()).isNotNull();
		assertThat(result.getBody().getEmail()).isEqualTo("ccc@gmail.com");
		assertThat(result.getBody().getNickname()).isEqualTo("ccc");
		assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.PENDING);
		assertThat(testContainer.userRepository.getById(1).getCertificationCode()).isEqualTo(
			"aaaaaaaa-aaaaaaaa-aaaaaaa");
	}
}