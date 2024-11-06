package com.tistory.jaimemin.springboottestdemo.spring.user.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tistory.jaimemin.springboottestdemo.spring.user.domain.UserStatus;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByIdAndStatus(long id, UserStatus userStatus);

	Optional<UserEntity> findByEmailAndStatus(String email, UserStatus userStatus);
}
