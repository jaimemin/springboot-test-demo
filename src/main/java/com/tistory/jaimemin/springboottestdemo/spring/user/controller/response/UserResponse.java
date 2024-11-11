package com.tistory.jaimemin.springboottestdemo.spring.user.controller.response;

import com.tistory.jaimemin.springboottestdemo.spring.user.domain.User;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.UserStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

	private Long id;

	private String email;

	private String nickname;

	private UserStatus status;

	private Long lastLoginAt;

	public static UserResponse from(User user) {
		return UserResponse.builder()
			.id(user.getId())
			.email(user.getEmail())
			.nickname(user.getNickname())
			.status(user.getStatus())
			.lastLoginAt(user.getLastLoginAt())
			.build();
	}
}
