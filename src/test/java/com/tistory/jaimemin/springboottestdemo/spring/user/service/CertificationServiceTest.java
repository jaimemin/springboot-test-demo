package com.tistory.jaimemin.springboottestdemo.spring.user.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.tistory.jaimemin.springboottestdemo.spring.mock.FakeMailSender;

class CertificationServiceTest {

	@Test
	public void 이메일과_컨텐츠가_제대로_만들어져서_보내지는지_테스트한다() {
		// given
		FakeMailSender fakeMailSender = new FakeMailSender();
		CertificationService certificationService = new CertificationService(fakeMailSender);
		long userId = 1;
		String certificationCode = "aaaaaaaa-aaaaaaaa-aaaaaaa";

		// when
		certificationService.send("aaa@gmail.com", userId, certificationCode);

		// then
		assertThat(fakeMailSender.email).isEqualTo("aaa@gmail.com");
		assertThat(fakeMailSender.title).isEqualTo("Please certify your email address");
		assertThat(fakeMailSender.content).isEqualTo(
			"Please click the following link to certify your email address: " + "http://localhost:8080/api/users/"
				+ userId + "/verify?certificationCode="
				+ certificationCode);
	}
}