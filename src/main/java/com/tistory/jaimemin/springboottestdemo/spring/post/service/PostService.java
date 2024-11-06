package com.tistory.jaimemin.springboottestdemo.spring.post.service;

import java.time.Clock;

import org.springframework.stereotype.Service;

import com.tistory.jaimemin.springboottestdemo.spring.common.domain.exception.ResourceNotFoundException;
import com.tistory.jaimemin.springboottestdemo.spring.post.domain.PostCreate;
import com.tistory.jaimemin.springboottestdemo.spring.post.domain.PostUpdate;
import com.tistory.jaimemin.springboottestdemo.spring.post.infrastructure.PostEntity;
import com.tistory.jaimemin.springboottestdemo.spring.post.infrastructure.PostRepository;
import com.tistory.jaimemin.springboottestdemo.spring.user.infrastructure.UserEntity;
import com.tistory.jaimemin.springboottestdemo.spring.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	private final UserService userService;

	public PostEntity getById(long id) {
		return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
	}

	public PostEntity create(PostCreate postCreate) {
		UserEntity userEntity = userService.getById(postCreate.getWriterId());
		PostEntity postEntity = new PostEntity();
		postEntity.setWriter(userEntity);
		postEntity.setContent(postCreate.getContent());
		postEntity.setCreatedAt(Clock.systemUTC().millis());

		return postRepository.save(postEntity);
	}

	public PostEntity update(long id, PostUpdate postUpdate) {
		PostEntity postEntity = getById(id);
		postEntity.setContent(postUpdate.getContent());
		postEntity.setModifiedAt(Clock.systemUTC().millis());

		return postRepository.save(postEntity);
	}
}