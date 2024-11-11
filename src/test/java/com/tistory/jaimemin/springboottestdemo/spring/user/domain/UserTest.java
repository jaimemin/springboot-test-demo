package com.tistory.jaimemin.springboottestdemo.spring.user.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.tistory.jaimemin.springboottestdemo.spring.common.domain.exception.CertificationCodeNotMatchedException;
import com.tistory.jaimemin.springboottestdemo.spring.mock.TestClockHolder;
import com.tistory.jaimemin.springboottestdemo.spring.mock.TestUuidHolder;

public class UserTest {

	@Test
	public void UserCreate_객체로_생성할_수_있다() {
		//given
		UserCreate userCreate = UserCreate.builder()
			.email("bbb@gmail.com")
			.address("Gyeongi")
			.nickname("bbb")
			.build();

		// when
		User user = User.from(userCreate, new TestUuidHolder("aaaaaaaa-aaaaaaaa-aaaaaba"));

		// then
		assertThat(user.getEmail()).isEqualTo("bbb@gmail.com");
		assertThat(user.getAddress()).isEqualTo("Gyeongi");
		assertThat(user.getNickname()).isEqualTo("bbb");
		assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
		assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaa-aaaaaaaa-aaaaaba");
	}

	@Test
	public void UserUpdate_객체로_데이터를_업데이트할_수_있다() {
		//given
		User user = User.builder()
			.id(1L)
			.email("aaa@gmail.com")
			.nickname("aaa")
			.address("Seoul")
			.status(UserStatus.ACTIVE)
			.lastLoginAt(100L)
			.certificationCode("aaaaaaaa-aaaaaaaa-aaaaaba")
			.build();
		UserUpdate userUpdate = UserUpdate.builder()
			.address("Seoul")
			.nickname("bbbgo")
			.build();

		// when
		user = user.update(userUpdate);

		// then
		assertThat(user.getId()).isEqualTo(1L);
		assertThat(user.getEmail()).isEqualTo("aaa@gmail.com");
		assertThat(user.getAddress()).isEqualTo("Seoul");
		assertThat(user.getNickname()).isEqualTo("bbbgo");
		assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
		assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaa-aaaaaaaa-aaaaaba");
		assertThat(user.getLastLoginAt()).isEqualTo(100L);
	}

	@Test
	public void 로그인을_할_수_있고_로그인시_마지막_로그인_시간이_변경된다() {
		//given
		User user = User.builder()
			.id(1L)
			.email("aaa@gmail.com")
			.nickname("aaa")
			.address("Seoul")
			.status(UserStatus.ACTIVE)
			.lastLoginAt(100L)
			.certificationCode("aaaaaaaa-aaaaaaaa-aaaaaba")
			.build();

		// when
		user.login(new TestClockHolder(100L));

		// then
		assertThat(user.getLastLoginAt()).isEqualTo(100L);
	}

	@Test
	public void 유효한_인증_코드로_계정을_활성화_할_수_있다() {
		//given
		User user = User.builder()
			.id(1L)
			.email("aaa@gmail.com")
			.nickname("aaa")
			.address("Seoul")
			.status(UserStatus.PENDING)
			.lastLoginAt(100L)
			.certificationCode("aaaaaaaa-aaaaaaaa-aaaaaba")
			.build();

		// when
		user = user.certificate("aaaaaaaa-aaaaaaaa-aaaaaba");

		// then
		assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
	}

	@Test
	public void 잘못된_인증_코드로_계정을_활성화_시도하면_에러를_던진다() {
		//given
		User user = User.builder()
			.id(1L)
			.email("aaa@gmail.com")
			.nickname("aaa")
			.address("Seoul")
			.status(UserStatus.PENDING)
			.lastLoginAt(100L)
			.certificationCode("aaaaaaaa-aaaaaaaa-aaaaaba")
			.build();

		// when
		// then
		assertThatThrownBy(() -> {
			user.certificate("aaaaaaaa-aaaaaaaa-aaaaabdsaa");
		}).isInstanceOf(CertificationCodeNotMatchedException.class);
	}
}
