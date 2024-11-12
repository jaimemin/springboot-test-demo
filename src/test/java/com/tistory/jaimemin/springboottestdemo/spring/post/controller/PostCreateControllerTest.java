package com.tistory.jaimemin.springboottestdemo.spring.post.controller;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.tistory.jaimemin.springboottestdemo.spring.mock.TestContainer;
import com.tistory.jaimemin.springboottestdemo.spring.post.controller.response.PostResponse;
import com.tistory.jaimemin.springboottestdemo.spring.post.domain.PostCreate;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.User;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.UserStatus;

class PostCreateControllerTest {

	@Test
	void 사용자는_게시물을_작성할_수_있다() throws Exception {
		// given
		TestContainer testContainer = TestContainer.builder()
			.clockHolder(() -> 1678530673958L)
			.build();
		testContainer.userRepository.save(User.builder()
			.id(1L)
			.email("aaa@gmail.com")
			.nickname("aaa")
			.address("Seoul")
			.certificationCode("aaaaaaaa-aaaaaaaa-aaaaaaa")
			.status(UserStatus.PENDING)
			.lastLoginAt(100L)
			.build());
		PostCreate postCreate = PostCreate.builder()
			.writerId(1L)
			.content("hello world")
			.build();

		// when
		ResponseEntity<PostResponse> result = testContainer.postCreateController.createPost(postCreate);

		// then
		assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
		assertThat(result.getBody().getContent()).isEqualTo("hello world");
		assertThat(result.getBody().getWriter().getNickname()).isEqualTo("aaa");
		assertThat(result.getBody().getCreatedAt()).isEqualTo(1678530673958L);
	}
}