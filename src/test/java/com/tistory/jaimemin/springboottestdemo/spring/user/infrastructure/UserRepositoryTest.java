package com.tistory.jaimemin.springboottestdemo.spring.user.infrastructure;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tistory.jaimemin.springboottestdemo.spring.user.domain.UserStatus;

@DataJpaTest(showSql = true)
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:test-application.properties")
@Sql("/sql/user-repository-test-data.sql")
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	void findByIdAndStatus_로_유저_데이터를_찾아올_수_있다() {
		// given
		// when
		UserEntity result = userRepository.findByIdAndStatus(1, UserStatus.ACTIVE).orElse(null);

		// then
		assertThat(result).isNotNull();
	}

	@Test
	void findByIdAndStatus_는_데이터가_없으면_Optional_Empty_를_내려준다() {
		// given
		// when
		UserEntity result = userRepository.findByIdAndStatus(1, UserStatus.PENDING).orElse(null);

		// then
		assertThat(result).isNull();
	}

	@Test
	void findByEmailAndStatus_로_유저_데이터를_찾아올_수_있다() {
		// given
		// when
		UserEntity result = userRepository.findByEmailAndStatus("aaa@gmail.com", UserStatus.ACTIVE).orElse(null);

		// then
		assertThat(result).isNotNull();
	}

	@Test
	void findByEmailAndStatus_는_데이터가_없으면_Optional_Empty_를_내려준다() {
		// given
		// when
		UserEntity result = userRepository.findByEmailAndStatus("aaa@gmail.com", UserStatus.PENDING).orElse(null);

		// then
		assertThat(result).isNull();
	}
}