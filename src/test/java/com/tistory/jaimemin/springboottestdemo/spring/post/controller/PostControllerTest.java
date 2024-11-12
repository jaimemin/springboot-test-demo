package com.tistory.jaimemin.springboottestdemo.spring.post.controller;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.tistory.jaimemin.springboottestdemo.spring.common.domain.exception.ResourceNotFoundException;
import com.tistory.jaimemin.springboottestdemo.spring.mock.TestContainer;
import com.tistory.jaimemin.springboottestdemo.spring.post.controller.response.PostResponse;
import com.tistory.jaimemin.springboottestdemo.spring.post.domain.Post;
import com.tistory.jaimemin.springboottestdemo.spring.post.domain.PostUpdate;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.User;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.UserStatus;

class PostControllerTest {

	@Test
	void 사용자는_게시물을_단건_조회_할_수_있다() throws Exception {
		// given
		TestContainer testContainer = TestContainer.builder()
			.build();
		User user = User.builder()
			.id(1L)
			.email("aaa@gmail.com")
			.nickname("aaa")
			.address("Seoul")
			.certificationCode("aaaaaaaa-aaaaaaaa-aaaaaaa")
			.status(UserStatus.ACTIVE)
			.lastLoginAt(100L)
			.build();
		testContainer.userRepository.save(user);
		testContainer.postRepository.save(Post.builder()
			.id(1L)
			.content("helloworld")
			.writer(user)
			.createdAt(100L)
			.build());

		// when
		ResponseEntity<PostResponse> result = testContainer.postController.getPostById(1L);

		// then
		assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
		assertThat(result.getBody()).isNotNull();
		assertThat(result.getBody().getContent()).isEqualTo("helloworld");
		assertThat(result.getBody().getWriter().getNickname()).isEqualTo("aaa");
		assertThat(result.getBody().getCreatedAt()).isEqualTo(100L);
	}

	@Test
	void 사용자가_존재하지_않는_게시물을_조회할_경우_에러가_난다() throws Exception {
		// given
		TestContainer testContainer = TestContainer.builder()
			.build();

		// when
		// then
		assertThatThrownBy(() -> {
			testContainer.postController.getPostById(1);
		}).isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	void 사용자는_게시물을_수정_할_수_있다() throws Exception {
		// given
		TestContainer testContainer = TestContainer.builder()
			.clockHolder(() -> 200L)
			.build();
		User user = User.builder()
			.id(1L)
			.email("aaa@gmail.com")
			.nickname("aaa")
			.address("Seoul")
			.certificationCode("aaaaaaaa-aaaaaaaa-aaaaaaa")
			.status(UserStatus.ACTIVE)
			.lastLoginAt(100L)
			.build();
		testContainer.userRepository.save(user);
		testContainer.postRepository.save(Post.builder()
			.id(1L)
			.content("helloworld")
			.writer(user)
			.createdAt(100L)
			.build());

		// when
		ResponseEntity<PostResponse> result = testContainer.postController.updatePost(1L, PostUpdate.builder()
			.content("foobar")
			.build());

		// then
		assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
		assertThat(result.getBody()).isNotNull();
		assertThat(result.getBody().getContent()).isEqualTo("foobar");
		assertThat(result.getBody().getWriter().getNickname()).isEqualTo("aaa");
		assertThat(result.getBody().getCreatedAt()).isEqualTo(100L);
		assertThat(result.getBody().getModifiedAt()).isEqualTo(200L);
	}
}