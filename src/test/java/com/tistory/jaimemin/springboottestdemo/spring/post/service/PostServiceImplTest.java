package com.tistory.jaimemin.springboottestdemo.spring.post.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tistory.jaimemin.springboottestdemo.spring.mock.FakePostRepository;
import com.tistory.jaimemin.springboottestdemo.spring.mock.FakeUserRepository;
import com.tistory.jaimemin.springboottestdemo.spring.mock.TestClockHolder;
import com.tistory.jaimemin.springboottestdemo.spring.post.domain.Post;
import com.tistory.jaimemin.springboottestdemo.spring.post.domain.PostCreate;
import com.tistory.jaimemin.springboottestdemo.spring.post.domain.PostUpdate;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.User;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.UserStatus;

class PostServiceImplTest {

	private PostServiceImpl postService;

	@BeforeEach
	void init() {
		FakePostRepository fakePostRepository = new FakePostRepository();
		FakeUserRepository fakeUserRepository = new FakeUserRepository();
		User writer = User.builder()
			.id(1L)
			.email("aaa@gmail.com")
			.nickname("aaa")
			.address("Seoul")
			.certificationCode("aaaaaaaa-aaaaaaaa-aaaaaaa")
			.status(UserStatus.ACTIVE)
			.lastLoginAt(0L)
			.build();

		this.postService = PostServiceImpl.builder()
			.postRepository(fakePostRepository)
			.userRepository(fakeUserRepository)
			.clockHolder(new TestClockHolder(1678530673958L))
			.build();
		fakeUserRepository.save(writer);
		fakeUserRepository.save(User.builder()
			.id(2L)
			.email("aadda@gmail.com")
			.nickname("aadda")
			.address("Seoul")
			.certificationCode("aaaaaaaa-aaaaaaaa-aaaaaba")
			.status(UserStatus.PENDING)
			.lastLoginAt(0L)
			.build());
		fakePostRepository.save(Post.builder()
			.id(1L)
			.content("helloworld")
			.createdAt(1678530673958L)
			.modifiedAt(1678530673958L)
			.writer(writer)
			.build());
	}

	@Test
	void getById는_존재하는_게시물을_내려준다() {
		// given
		// when
		Post result = postService.getById(1L);

		// then
		assertThat(result.getContent()).isEqualTo("helloworld");
		assertThat(result.getWriter().getEmail()).isEqualTo("aaa@gmail.com");
	}

	@Test
	void postCreateDto_를_이용해_게시물을_생성할_수_있다() {
		// given
		PostCreate postCreate = PostCreate.builder()
			.writerId(1)
			.content("foobar")
			.build();

		// when
		Post result = postService.create(postCreate);

		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getContent()).isEqualTo("foobar");
		assertThat(result.getCreatedAt()).isGreaterThan(0L);
		assertThat(result.getCreatedAt()).isEqualTo(1678530673958L);
	}

	@Test
	void postUpdateDto_를_이용해_게시물을_수정할_수_있다() {
		// given
		PostUpdate postUpdate = PostUpdate.builder()
			.content("hello world :)")
			.build();

		// when
		postService.update(1, postUpdate);

		// then
		Post result = postService.getById(1L);
		assertThat(result.getContent()).isEqualTo("hello world :)");
		assertThat(result.getModifiedAt()).isGreaterThan(0L);
		assertThat(result.getModifiedAt()).isEqualTo(1678530673958L);
	}
}