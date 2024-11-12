package com.tistory.jaimemin.springboottestdemo.spring.user.controller.port;

import com.tistory.jaimemin.springboottestdemo.spring.user.domain.User;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.UserCreate;

public interface UserCreateService {

	User create(UserCreate userCreate);
}
