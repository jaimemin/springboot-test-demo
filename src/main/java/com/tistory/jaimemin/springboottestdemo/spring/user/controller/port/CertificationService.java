package com.tistory.jaimemin.springboottestdemo.spring.user.controller.port;

public interface CertificationService {

	void send(String email, long id, String certificationCode);
}
