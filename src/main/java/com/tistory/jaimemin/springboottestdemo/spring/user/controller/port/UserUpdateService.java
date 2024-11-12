package com.tistory.jaimemin.springboottestdemo.spring.user.controller.port;

import com.tistory.jaimemin.springboottestdemo.spring.user.domain.User;
import com.tistory.jaimemin.springboottestdemo.spring.user.domain.UserUpdate;

public interface UserUpdateService {

	User update(long id, UserUpdate userUpdate);
}
