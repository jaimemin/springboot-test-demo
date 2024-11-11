package com.tistory.jaimemin.springboottestdemo.spring.user.service.port;

import java.util.Optional;

import com.tistory.jaimemin.springboottestdemo.spring.user.domain.User;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.UserStatus;

public interface UserRepository {

	User getById(long id);

	Optional<User> findById(long id);

	Optional<User> findByIdAndStatus(long id, UserStatus userStatus);

	Optional<User> findByEmailAndStatus(String email, UserStatus userStatus);

	User save(User user);
}
