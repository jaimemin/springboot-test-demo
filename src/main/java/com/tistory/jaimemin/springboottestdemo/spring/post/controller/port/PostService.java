package com.tistory.jaimemin.springboottestdemo.spring.post.controller.port;

import com.tistory.jaimemin.springboottestdemo.spring.post.domain.Post;
import com.tistory.jaimemin.springboottestdemo.spring.post.domain.PostCreate;
import com.tistory.jaimemin.springboottestdemo.spring.post.domain.PostUpdate;

public interface PostService {

	Post getById(long id);

	Post create(PostCreate postCreate);

	Post update(long id, PostUpdate postUpdate);
}
