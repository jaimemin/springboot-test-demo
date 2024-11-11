package com.tistory.jaimemin.springboottestdemo.spring.post.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.tistory.jaimemin.springboottestdemo.spring.mock.TestClockHolder;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.User;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.UserStatus;

public class PostTest {

	@Test
	public void PostCreate으로_게시물을_만들_수_있다() {
		// given
		PostCreate postCreate = PostCreate.builder()
			.writerId(1)
			.content("helloworld")
			.build();
		User writer = User.builder()
			.id(1L)
			.email("aaa@gmail.com")
			.nickname("aaa")
			.address("Seoul")
			.status(UserStatus.ACTIVE)
			.certificationCode("aaaaaaaa-aaaaaaaa-aaaaaba")
			.build();

		// when
		Post post = Post.from(writer, postCreate, new TestClockHolder(100L));

		// then
		assertThat(post.getContent()).isEqualTo("helloworld");
		assertThat(post.getWriter().getId()).isEqualTo(1);
		assertThat(post.getWriter().getEmail()).isEqualTo("aaa@gmail.com");
		assertThat(post.getWriter().getNickname()).isEqualTo("aaa");
		assertThat(post.getWriter().getAddress()).isEqualTo("Seoul");
		assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
		assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaaaaaa-aaaaaaaa-aaaaaba");
		assertThat(post.getCreatedAt()).isEqualTo(100L);
	}
}
