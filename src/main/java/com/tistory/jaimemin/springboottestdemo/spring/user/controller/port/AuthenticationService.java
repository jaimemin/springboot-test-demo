package com.tistory.jaimemin.springboottestdemo.spring.user.controller.port;

public interface AuthenticationService {

	void login(long id);

	void verifyEmail(long id, String certificationCode);
}
