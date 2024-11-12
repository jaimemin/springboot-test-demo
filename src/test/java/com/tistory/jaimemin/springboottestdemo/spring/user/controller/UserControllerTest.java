package com.tistory.jaimemin.springboottestdemo.spring.user.controller;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.tistory.jaimemin.springboottestdemo.spring.common.domain.exception.CertificationCodeNotMatchedException;
import com.tistory.jaimemin.springboottestdemo.spring.common.domain.exception.ResourceNotFoundException;
import com.tistory.jaimemin.springboottestdemo.spring.common.service.port.ClockHolder;
import com.tistory.jaimemin.springboottestdemo.spring.mock.TestContainer;
import com.tistory.jaimemin.springboottestdemo.spring.user.controller.response.MyProfileResponse;
import com.tistory.jaimemin.springboottestdemo.spring.user.controller.response.UserResponse;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.User;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.UserStatus;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.UserUpdate;

class UserControllerTest {

	@Test
	void 사용자는_특정_유저의_정보를_전달_받을_수_있다() throws Exception {
		// given
		TestContainer testContainer = TestContainer.builder()
			.build();
		testContainer.userRepository.save(User.builder()
			.id(1L)
			.email("aaa@gmail.com")
			.nickname("aaa")
			.address("Seoul")
			.certificationCode("aaaaaaaa-aaaaaaaa-aaaaaaa")
			.status(UserStatus.ACTIVE)
			.lastLoginAt(100L)
			.build());

		// when
		ResponseEntity<UserResponse> result = testContainer.userController.getUserById(1);

		// then
		assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
		assertThat(result.getBody()).isNotNull();
		assertThat(result.getBody().getEmail()).isEqualTo("aaa@gmail.com");
		assertThat(result.getBody().getNickname()).isEqualTo("aaa");
		assertThat(result.getBody().getLastLoginAt()).isEqualTo(100L);
		assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
	}

	@Test
	void 사용자는_존재하지_않는_유저의_아이디로_api_호출할_경우_404_응답을_받는다() throws Exception {
		// given
		TestContainer testContainer = TestContainer.builder()
			.build();

		// when
		// then
		assertThatThrownBy(() -> {
			testContainer.userController.getUserById(1);
		}).isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	void 사용자는_인증_코드로_계정을_활성화_시킬_수_있다() throws Exception {
		// given
		TestContainer testContainer = TestContainer.builder()
			.build();
		testContainer.userRepository.save(User.builder()
			.id(1L)
			.email("aaa@gmail.com")
			.nickname("aaa")
			.address("Seoul")
			.certificationCode("aaaaaaaa-aaaaaaaa-aaaaaaa")
			.status(UserStatus.PENDING)
			.lastLoginAt(100L)
			.build());

		// when
		ResponseEntity<Void> result = testContainer.userController.verifyEmail(1, "aaaaaaaa-aaaaaaaa-aaaaaaa");

		// then
		assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(302));
		assertThat(testContainer.userRepository.getById(1).getStatus()).isEqualTo(UserStatus.ACTIVE);
	}

	@Test
	void 사용자는_인증_코드가_일치하지_않을_경우_권한_없음_에러를_내려준다() throws Exception {
		// given
		TestContainer testContainer = TestContainer.builder()
			.build();
		testContainer.userRepository.save(User.builder()
			.id(1L)
			.email("aaa@gmail.com")
			.nickname("aaa")
			.address("Seoul")
			.certificationCode("aaaaaaaa-aaaaaaaa-aaaaaaba")
			.status(UserStatus.PENDING)
			.lastLoginAt(100L)
			.build());

		// when
		// then
		assertThatThrownBy(() -> {
			testContainer.userController.verifyEmail(1, "aaaaaaaa-aaaaaaaa-aaaaaaa");
		}).isInstanceOf(CertificationCodeNotMatchedException.class);
	}

	@Test
	void 사용자는_내_정보를_불러올_때_개인정보인_주소도_갖고_올_수_있다() {
		// given
		TestContainer testContainer = TestContainer.builder()
			.clockHolder(new ClockHolder() {
				@Override
				public long millis() {
					return 1678530673958L;
				}
			})
			.build();
		testContainer.userRepository.save(User.builder()
			.id(1L)
			.email("aaa@gmail.com")
			.nickname("aaa")
			.address("Seoul")
			.certificationCode("aaaaaaaa-aaaaaaaa-aaaaaaa")
			.status(UserStatus.ACTIVE)
			.lastLoginAt(100L)
			.build());

		// when
		ResponseEntity<MyProfileResponse> result = testContainer.userController.getMyInfo("aaa@gmail.com");

		// then
		assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
		assertThat(result.getBody()).isNotNull();
		assertThat(result.getBody().getEmail()).isEqualTo("aaa@gmail.com");
		assertThat(result.getBody().getNickname()).isEqualTo("aaa");
		assertThat(result.getBody().getLastLoginAt()).isEqualTo(1678530673958L);
		assertThat(result.getBody().getAddress()).isEqualTo("Seoul");
		assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
	}

	@Test
	void 사용자는_내_정보를_수정할_수_있다() {
		// given
		TestContainer testContainer = TestContainer.builder()
			.build();
		testContainer.userRepository.save(User.builder()
			.id(1L)
			.email("aaa@gmail.com")
			.nickname("aaa")
			.address("Seoul")
			.certificationCode("aaaaaaaa-aaaaaaaa-aaaaaaa")
			.status(UserStatus.ACTIVE)
			.lastLoginAt(100L)
			.build());

		// when
		ResponseEntity<MyProfileResponse> result = testContainer.userController.updateMyInfo("aaa@gmail.com",
			UserUpdate.builder()
				.address("Pangyo")
				.nickname("aabba")
				.build());

		// then
		assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
		assertThat(result.getBody()).isNotNull();
		assertThat(result.getBody().getEmail()).isEqualTo("aaa@gmail.com");
		assertThat(result.getBody().getNickname()).isEqualTo("aabba");
		assertThat(result.getBody().getLastLoginAt()).isEqualTo(100L);
		assertThat(result.getBody().getAddress()).isEqualTo("Pangyo");
		assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
	}
}