package com.tistory.jaimemin.springboottestdemo.spring.user.service.port;

public interface MailSender {

	void send(String email, String title, String content);
}
