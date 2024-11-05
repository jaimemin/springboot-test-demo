package com.tistory.jaimemin.springboottestdemo.spring.service;

import java.time.Clock;

import org.springframework.stereotype.Service;

import com.tistory.jaimemin.springboottestdemo.spring.exception.ResourceNotFoundException;
import com.tistory.jaimemin.springboottestdemo.spring.model.dto.PostCreateDto;
import com.tistory.jaimemin.springboottestdemo.spring.model.dto.PostUpdateDto;
import com.tistory.jaimemin.springboottestdemo.spring.repository.PostEntity;
import com.tistory.jaimemin.springboottestdemo.spring.repository.PostRepository;
import com.tistory.jaimemin.springboottestdemo.spring.repository.UserEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	private final UserService userService;

	public PostEntity getById(long id) {
		return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
	}

	public PostEntity create(PostCreateDto postCreateDto) {
		UserEntity userEntity = userService.getById(postCreateDto.getWriterId());
		PostEntity postEntity = new PostEntity();
		postEntity.setWriter(userEntity);
		postEntity.setContent(postCreateDto.getContent());
		postEntity.setCreatedAt(Clock.systemUTC().millis());

		return postRepository.save(postEntity);
	}

	public PostEntity update(long id, PostUpdateDto postUpdateDto) {
		PostEntity postEntity = getById(id);
		postEntity.setContent(postUpdateDto.getContent());
		postEntity.setModifiedAt(Clock.systemUTC().millis());

		return postRepository.save(postEntity);
	}
}