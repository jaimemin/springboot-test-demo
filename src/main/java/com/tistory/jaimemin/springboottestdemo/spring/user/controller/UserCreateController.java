package com.tistory.jaimemin.springboottestdemo.spring.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tistory.jaimemin.springboottestdemo.spring.user.controller.response.UserResponse;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.UserCreate;
import com.tistory.jaimemin.springboottestdemo.spring.user.infrastructure.UserEntity;
import com.tistory.jaimemin.springboottestdemo.spring.user.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "유저(users)")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserCreateController {

	private final UserController userController;

	private final UserService userService;

	@PostMapping
	public ResponseEntity<UserResponse> createUser(@RequestBody UserCreate userCreate) {
		UserEntity userEntity = userService.create(userCreate);

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(userController.toResponse(userEntity));
	}

}