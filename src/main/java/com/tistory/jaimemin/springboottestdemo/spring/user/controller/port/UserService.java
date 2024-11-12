package com.tistory.jaimemin.springboottestdemo.spring.user.controller.port;

import com.tistory.jaimemin.springboottestdemo.spring.user.domain.User;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.UserCreate;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.UserUpdate;

public interface UserService {

	User getByEmail(String email);

	User getById(long id);

	User create(UserCreate userCreate);

	User update(long id, UserUpdate userUpdate);
}
