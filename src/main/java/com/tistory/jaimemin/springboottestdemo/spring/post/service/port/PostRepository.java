package com.tistory.jaimemin.springboottestdemo.spring.post.service.port;

import java.util.Optional;

import com.tistory.jaimemin.springboottestdemo.spring.post.domain.Post;

public interface PostRepository {

	Optional<Post> findById(long id);

	Post save(Post post);
}
