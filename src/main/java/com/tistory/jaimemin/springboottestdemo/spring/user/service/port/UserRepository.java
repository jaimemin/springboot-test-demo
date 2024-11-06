package com.tistory.jaimemin.springboottestdemo.spring.user.service.port;

import java.util.Optional;

import com.tistory.jaimemin.springboottestdemo.spring.user.domain.UserStatus;
import com.tistory.jaimemin.springboottestdemo.spring.user.infrastructure.UserEntity;

public interface UserRepository {

	Optional<UserEntity> findById(long id);

	Optional<UserEntity> findByIdAndStatus(long id, UserStatus userStatus);

	Optional<UserEntity> findByEmailAndStatus(String email, UserStatus userStatus);

	UserEntity save(UserEntity userEntity);
}
