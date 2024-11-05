package com.tistory.jaimemin.springboottestdemo.spring.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import com.tistory.jaimemin.springboottestdemo.spring.model.dto.PostCreateDto;
import com.tistory.jaimemin.springboottestdemo.spring.model.dto.PostUpdateDto;
import com.tistory.jaimemin.springboottestdemo.spring.repository.PostEntity;

@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@SqlGroup({
	@Sql(value = "/sql/post-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
	@Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class PostServiceTest {

	@Autowired
	private PostService postService;

	@Test
	void getById는_존재하는_게시물을_내려준다() {
		// given
		// when
		PostEntity result = postService.getById(1L);

		// then
		assertThat(result.getContent()).isEqualTo("helloworld");
		assertThat(result.getWriter().getEmail()).isEqualTo("aaa@gmail.com");
	}

	@Test
	void postCreateDto_를_이용해_게시물을_생성할_수_있다() {
		// given
		PostCreateDto postCreateDto = PostCreateDto.builder()
			.writerId(1)
			.content("foobar")
			.build();

		// when
		PostEntity result = postService.create(postCreateDto);

		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getContent()).isEqualTo("foobar");
		assertThat(result.getCreatedAt()).isGreaterThan(0L);
	}

	@Test
	void postUpdateDto_를_이용해_게시물을_수정할_수_있다() {
		// given
		PostUpdateDto postUpdateDto = PostUpdateDto.builder()
			.content("hello world :)")
			.build();

		// when
		postService.update(1, postUpdateDto);

		// then
		PostEntity result = postService.getById(1L);
		assertThat(result.getContent()).isEqualTo("hello world :)");
		assertThat(result.getModifiedAt()).isGreaterThan(0L);
	}
}