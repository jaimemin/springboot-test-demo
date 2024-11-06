package com.tistory.jaimemin.springboottestdemo.spring.user.controller;

import static org.assertj.core.api.Assertions.*;
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
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.UserStatus;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.UserUpdate;
import com.tistory.jaimemin.springboottestdemo.spring.user.infrastructure.UserEntity;
import com.tistory.jaimemin.springboottestdemo.spring.user.infrastructure.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup({
	@Sql(value = "/sql/user-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
	@Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void 사용자는_특정_유저의_정보를_전달_받을_수_있다() throws Exception {
		// given
		// when
		// then
		mockMvc.perform(get("/api/users/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.email").value("aaa@gmail.com"))
			.andExpect(jsonPath("$.nickname").value("aaa"))
			.andExpect(jsonPath("$.address").doesNotExist())
			.andExpect(jsonPath("$.status").value("ACTIVE"));
	}

	@Test
	void 사용자는_존재하지_않는_유저의_아이디로_api_호출할_경우_404_응답을_받는다() throws Exception {
		// given
		// when
		// then
		mockMvc.perform(get("/api/users/0"))
			.andExpect(status().isNotFound())
			.andExpect(content().string("Users에서 ID 0를 찾을 수 없습니다."));
	}

	@Test
	void 사용자는_인증_코드로_계정을_활성화_시킬_수_있다() throws Exception {
		// given
		// when
		// then
		mockMvc.perform(get("/api/users/2/verify").queryParam("certificationCode", "aaaaaaaa-aaaaaaaa-aaaaaba"))
			.andExpect(status().is3xxRedirection());
		UserEntity userEntity = userRepository.findById(2L).get();

		assertThat(userEntity.getStatus()).isEqualTo(UserStatus.ACTIVE);
	}

	@Test
	void 사용자는_인증_코드가_일치하지_않을_경우_권한_없음_에러를_내려준다() throws Exception {
		// given
		// when
		// then
		mockMvc.perform(get("/api/users/2/verify").queryParam("certificationCode", "aaaaaaaa-aaaaaaaa-aaaaabaccc"))
			.andExpect(status().isForbidden());
	}

	@Test
	void 사용자는_내_정보를_불러올_때_개인정보인_주소도_갖고_올_수_있다() throws Exception {
		// given
		// when
		// then
		mockMvc.perform(
				get("/api/users/me")
					.header("EMAIL", "aaa@gmail.com"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.email").value("aaa@gmail.com"))
			.andExpect(jsonPath("$.nickname").value("aaa"))
			.andExpect(jsonPath("$.address").value("Seoul"))
			.andExpect(jsonPath("$.status").value("ACTIVE"));
	}

	@Test
	void 사용자는_내_정보를_수정할_수_있다() throws Exception {
		// given
		UserUpdate userUpdate = UserUpdate.builder()
			.nickname("aaa222")
			.address("Panama")
			.build();

		// when
		// then
		mockMvc.perform(
				put("/api/users/me")
					.header("EMAIL", "aaa@gmail.com")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(userUpdate))
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.email").value("aaa@gmail.com"))
			.andExpect(jsonPath("$.nickname").value("aaa222"))
			.andExpect(jsonPath("$.address").value("Panama"))
			.andExpect(jsonPath("$.status").value("ACTIVE"));
	}
}