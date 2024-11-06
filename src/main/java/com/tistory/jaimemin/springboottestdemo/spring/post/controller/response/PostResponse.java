package com.tistory.jaimemin.springboottestdemo.spring.post.controller.response;

import com.tistory.jaimemin.springboottestdemo.spring.user.controller.response.UserResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponse {

	private Long id;
	private String content;
	private Long createdAt;
	private Long modifiedAt;
	private UserResponse writer;
}
