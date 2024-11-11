package com.tistory.jaimemin.springboottestdemo.spring.medium;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tistory.jaimemin.springboottestdemo.spring.post.domain.PostCreate;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup({
	@Sql(value = "/sql/post-create-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
	@Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class PostCreateControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void 사용자는_게시물을_작성할_수_있다() throws Exception {
		// given
		PostCreate postCreate = PostCreate.builder()
			.writerId(1L)
			.content("hello world")
			.build();

		// when
		// then
		mockMvc.perform(
				post("/api/posts")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(postCreate))
			)
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").isNumber())
			.andExpect(jsonPath("$.content").value("hello world"))
			.andExpect(jsonPath("$.writer.id").isNumber())
			.andExpect(jsonPath("$.writer.email").value("aaa@gmail.com"))
			.andExpect(jsonPath("$.writer.nickname").value("aaa"));
	}
}