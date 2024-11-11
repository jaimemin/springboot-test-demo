package com.tistory.jaimemin.springboottestdemo.spring.post.controller.response;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.tistory.jaimemin.springboottestdemo.spring.post.domain.Post;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.User;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.UserStatus;

public class PostResponseTest {

	@Test
	public void Post로_응답을_생성할_수_있다() {
		// given
		Post post = Post.builder()
			.content("helloworld")
			.writer(User.builder()
				.email("aaa@gmail.com")
				.nickname("aaa")
				.address("Seoul")
				.status(UserStatus.ACTIVE)
				.certificationCode("aaaaaaaa-aaaaaaaa-aaaaaba")
				.build())
			.build();

		// when
		PostResponse postResponse = PostResponse.from(post);

		// then
		assertThat(postResponse.getContent()).isEqualTo("helloworld");
		assertThat(postResponse.getWriter().getEmail()).isEqualTo("aaa@gmail.com");
		assertThat(postResponse.getWriter().getNickname()).isEqualTo("aaa");
		assertThat(postResponse.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
	}
}
