package com.tistory.jaimemin.springboottestdemo.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

}