package com.tistory.jaimemin.springboottestdemo.spring.post.service.port;

import java.util.Optional;

import com.tistory.jaimemin.springboottestdemo.spring.post.infrastructure.PostEntity;

public interface PostRepository {

	Optional<PostEntity> findById(long id);

	PostEntity save(PostEntity postEntity);
}
