package com.tistory.jaimemin.springboottestdemo.spring.user.controller.port;

import com.tistory.jaimemin.springboottestdemo.spring.user.domain.User;

public interface UserReadService {

	User getByEmail(String email);

	User getById(long id);
}
