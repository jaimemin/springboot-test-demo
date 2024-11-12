package com.tistory.jaimemin.springboottestdemo.spring.user.service;

import org.springframework.stereotype.Service;

import com.tistory.jaimemin.springboottestdemo.spring.user.service.port.MailSender;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificationService
	implements com.tistory.jaimemin.springboottestdemo.spring.user.controller.port.CertificationService {

	private final MailSender mailSender;

	public void send(String email, long userId, String certificationCode) {
		String title = "Please certify your email address";
		String content =
			"Please click the following link to certify your email address: " + generateCertificationUrl(userId,
				certificationCode);

		mailSender.send(email, title, content);
	}

	public String generateCertificationUrl(long userId, String certificationCode) {
		return "http://localhost:8080/api/users/" + userId + "/verify?certificationCode="
			+ certificationCode;
	}
}
