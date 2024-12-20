package com.tistory.jaimemin.springboottestdemo.spring.user.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tistory.jaimemin.springboottestdemo.spring.common.domain.exception.CertificationCodeNotMatchedException;
import com.tistory.jaimemin.springboottestdemo.spring.common.domain.exception.ResourceNotFoundException;
import com.tistory.jaimemin.springboottestdemo.spring.mock.FakeMailSender;
import com.tistory.jaimemin.springboottestdemo.spring.mock.FakeUserRepository;
import com.tistory.jaimemin.springboottestdemo.spring.mock.TestClockHolder;
import com.tistory.jaimemin.springboottestdemo.spring.mock.TestUuidHolder;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.User;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.UserCreate;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.UserStatus;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.UserUpdate;

class UserServiceImplTest {

	private UserServiceImpl userService;

	@BeforeEach
	void init() {
		FakeMailSender fakeMailSender = new FakeMailSender();
		FakeUserRepository fakeUserRepository = new FakeUserRepository();

		this.userService = UserServiceImpl.builder()
			.uuidHolder(new TestUuidHolder("aaaaaaaa-aaaaaaaa-aaaaaba"))
			.clockHolder(new TestClockHolder(1678530673958L))
			.userRepository(fakeUserRepository)
			.certificationService(new CertificationService(fakeMailSender))
			.build();
		fakeUserRepository.save(User.builder()
			.id(1L)
			.email("aaa@gmail.com")
			.nickname("aaa")
			.address("Seoul")
			.certificationCode("aaaaaaaa-aaaaaaaa-aaaaaaa")
			.status(UserStatus.ACTIVE)
			.lastLoginAt(0L)
			.build());
		fakeUserRepository.save(User.builder()
			.id(2L)
			.email("aadda@gmail.com")
			.nickname("aadda")
			.address("Seoul")
			.certificationCode("aaaaaaaa-aaaaaaaa-aaaaaba")
			.status(UserStatus.PENDING)
			.lastLoginAt(0L)
			.build());
	}

	@Test
	void getByEmail은_ACTIVE_상태인_유저를_찾아올_수_있다() {
		// given
		String email = "aaa@gmail.com";

		// when
		User result = userService.getByEmail(email);

		// then
		assertThat(result.getNickname()).isEqualTo("aaa");
	}

	@Test
	void getByEmail은_PENDING_상태인_유저를_찾아올_수_없다() {
		// given
		String email = "aadda@gmail.com";

		// when
		// then
		assertThatThrownBy(() -> {
			User result = userService.getByEmail(email);
		}).isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	void getById는_ACTIVE_상태인_유저를_찾아올_수_있다() {
		// given
		// when
		User result = userService.getById(1);

		// then
		assertThat(result.getNickname()).isEqualTo("aaa");
	}

	@Test
	void getById는_PENDING_상태인_유저를_찾아올_수_없다() {
		// given
		// when
		// then
		assertThatThrownBy(() -> {
			User result = userService.getById(2);
		}).isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	void userCreateDto_를_이용해_유저를_생성할_수_있다() {
		// given
		UserCreate userCreate = UserCreate.builder()
			.email("bbb@gmail.com")
			.address("Gyeongi")
			.nickname("bbb")
			.build();

		// when
		User result = userService.create(userCreate);

		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
		assertThat(result.getCertificationCode()).isEqualTo("aaaaaaaa-aaaaaaaa-aaaaaba");
	}

	@Test
	void userUpdateDto_를_이용하여_유저를_수정할_수_있다() {
		// given
		UserUpdate userUpdate = UserUpdate.builder()
			.address("Incheon")
			.nickname("á22a2a2a")
			.build();

		// when
		userService.update(1, userUpdate);

		// then
		User user = userService.getById(1);
		assertThat(user.getId()).isNotNull();
		assertThat(user.getAddress()).isEqualTo("Incheon");
		assertThat(user.getNickname()).isEqualTo("á22a2a2a");
	}

	@Test
	void user를_로그인_시키면_마지막_로그인_시간이_변경된다() {
		// given
		// when
		userService.login(1);

		// then
		User user = userService.getById(1);
		assertThat(user.getLastLoginAt()).isGreaterThan(0L);
		assertThat(user.getLastLoginAt()).isEqualTo(1678530673958L);
	}

	@Test
	void PENDING_상태의_사용자는_인증_코드로_ACTIVE_시킬_수_있다() {
		// given
		// when
		userService.verifyEmail(2, "aaaaaaaa-aaaaaaaa-aaaaaba");

		// then
		User user = userService.getById(2);
		assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
	}

	@Test
	void PENDING_상태의_사용자는_잘못된_인증_코드를_받으면_에러를_던진다() {
		// given
		// when
		// then
		assertThatThrownBy(() -> {
			userService.verifyEmail(2, "aaaaaaaa-aaaaaaaa-aaaaabc");
		}).isInstanceOf(CertificationCodeNotMatchedException.class);
	}
}