package com.tistory.jaimemin.springboottestdemo.spring.mock;

import com.tistory.jaimemin.springboottestdemo.spring.common.service.port.UuidHolder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestUuidHolder implements UuidHolder {

	private final String uuid;

	@Override
	public String random() {
		return uuid;
	}
}
