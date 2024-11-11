package com.tistory.jaimemin.springboottestdemo.spring.mock;

import com.tistory.jaimemin.springboottestdemo.spring.common.service.port.ClockHolder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestClockHolder implements ClockHolder {

	private final long millis;

	@Override
	public long millis() {
		return millis;
	}
}
