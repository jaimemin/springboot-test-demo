package com.tistory.jaimemin.springboottestdemo.spring.user.infrastructure;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.tistory.jaimemin.springboottestdemo.spring.user.domain.User;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.UserStatus;
import com.tistory.jaimemin.springboottestdemo.spring.user.service.port.UserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

	private final UserJpaRepository userJpaRepository;

	@Override
	public Optional<User> findById(long id) {
		return userJpaRepository.findById(id).map(UserEntity::toModel);
	}

	@Override
	public Optional<User> findByIdAndStatus(long id, UserStatus userStatus) {
		return userJpaRepository.findByIdAndStatus(id, userStatus).map(UserEntity::toModel);
	}

	@Override
	public Optional<User> findByEmailAndStatus(String email, UserStatus userStatus) {
		return userJpaRepository.findByEmailAndStatus(email, userStatus).map(UserEntity::toModel);
	}

	@Override
	public User save(User user) {
		return userJpaRepository.save(UserEntity.fromModel(user)).toModel();
	}
}
