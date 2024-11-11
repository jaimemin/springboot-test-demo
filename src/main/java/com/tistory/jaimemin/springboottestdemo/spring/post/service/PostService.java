package com.tistory.jaimemin.springboottestdemo.spring.post.service;

import org.springframework.stereotype.Service;

import com.tistory.jaimemin.springboottestdemo.spring.common.domain.exception.ResourceNotFoundException;
import com.tistory.jaimemin.springboottestdemo.spring.post.domain.Post;
import com.tistory.jaimemin.springboottestdemo.spring.post.domain.PostCreate;
import com.tistory.jaimemin.springboottestdemo.spring.post.domain.PostUpdate;
import com.tistory.jaimemin.springboottestdemo.spring.post.service.port.PostRepository;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.User;
import com.tistory.jaimemin.springboottestdemo.spring.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	private final UserService userService;

	public Post getById(long id) {
		return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
	}

	public Post create(PostCreate postCreate) {
		User writer = userService.getById(postCreate.getWriterId());
		Post post = Post.from(writer, postCreate);

		return postRepository.save(post);
	}

	public Post update(long id, PostUpdate postUpdate) {
		Post post = getById(id);
		post = post.update(postUpdate);

		return postRepository.save(post);
	}
}