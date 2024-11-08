package com.tistory.jaimemin.springboottestdemo.spring.user.controller.response;

import com.tistory.jaimemin.springboottestdemo.spring.user.domain.UserStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyProfileResponse {

	private Long id;

	private String email;

	private String nickname;

	private String address;

	private UserStatus status;

	private Long lastLoginAt;
}
