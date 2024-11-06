package com.tistory.jaimemin.springboottestdemo.spring.post.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long> {

}