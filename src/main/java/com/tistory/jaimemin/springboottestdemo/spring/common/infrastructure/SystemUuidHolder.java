package com.tistory.jaimemin.springboottestdemo.spring.common.infrastructure;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.tistory.jaimemin.springboottestdemo.spring.common.service.port.UuidHolder;

@Component
public class SystemUuidHolder implements UuidHolder {

	@Override
	public String random() {
		return UUID.randomUUID().toString();
	}
}
