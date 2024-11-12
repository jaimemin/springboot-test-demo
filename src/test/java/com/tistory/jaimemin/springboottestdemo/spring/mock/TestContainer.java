package com.tistory.jaimemin.springboottestdemo.spring.mock;

import com.tistory.jaimemin.springboottestdemo.spring.common.service.port.ClockHolder;
import com.tistory.jaimemin.springboottestdemo.spring.common.service.port.UuidHolder;
import com.tistory.jaimemin.springboottestdemo.spring.post.controller.PostController;
import com.tistory.jaimemin.springboottestdemo.spring.post.controller.PostCreateController;
import com.tistory.jaimemin.springboottestdemo.spring.post.controller.port.PostService;
import com.tistory.jaimemin.springboottestdemo.spring.post.service.PostServiceImpl;
import com.tistory.jaimemin.springboottestdemo.spring.post.service.port.PostRepository;
import com.tistory.jaimemin.springboottestdemo.spring.user.controller.UserController;
import com.tistory.jaimemin.springboottestdemo.spring.user.controller.UserCreateController;
import com.tistory.jaimemin.springboottestdemo.spring.user.controller.port.AuthenticationService;
import com.tistory.jaimemin.springboottestdemo.spring.user.controller.port.UserCreateService;
import com.tistory.jaimemin.springboottestdemo.spring.user.controller.port.UserReadService;
import com.tistory.jaimemin.springboottestdemo.spring.user.controller.port.UserUpdateService;
import com.tistory.jaimemin.springboottestdemo.spring.user.service.CertificationService;
import com.tistory.jaimemin.springboottestdemo.spring.user.service.UserServiceImpl;
import com.tistory.jaimemin.springboottestdemo.spring.user.service.port.MailSender;
import com.tistory.jaimemin.springboottestdemo.spring.user.service.port.UserRepository;

import lombok.Builder;

public class TestContainer {

	public final MailSender mailSender;

	public final UserRepository userRepository;

	public final PostRepository postRepository;

	public final UserReadService userReadService;

	public final UserCreateService userCreateService;

	public final UserUpdateService userUpdateService;

	public final AuthenticationService authenticationService;

	public final PostService postService;

	public final CertificationService certificationService;

	public final UserController userController;

	public final UserCreateController userCreateController;

	public final PostController postController;

	public final PostCreateController postCreateController;

	@Builder
	public TestContainer(ClockHolder clockHolder, UuidHolder uuidHolder) {
		this.mailSender = new FakeMailSender();
		this.userRepository = new FakeUserRepository();
		this.postRepository = new FakePostRepository();
		this.certificationService = new CertificationService(this.mailSender);
		this.postService = PostServiceImpl.builder()
			.postRepository(this.postRepository)
			.userRepository(this.userRepository)
			.clockHolder(clockHolder)
			.build();
		UserServiceImpl userService = UserServiceImpl.builder()
			.uuidHolder(uuidHolder)
			.clockHolder(clockHolder)
			.userRepository(this.userRepository)
			.certificationService(this.certificationService)
			.build();
		this.userReadService = userService;
		this.userCreateService = userService;
		this.userUpdateService = userService;
		this.authenticationService = userService;
		this.userController = UserController.builder()
			.userReadService(this.userReadService)
			.userCreateService(this.userCreateService)
			.userUpdateService(this.userUpdateService)
			.authenticationService(this.authenticationService)
			.build();
		this.userCreateController = UserCreateController.builder()
			.userCreateService(this.userCreateService)
			.build();
		this.postController = PostController.builder()
			.postService(this.postService)
			.build();
		this.postCreateController = PostCreateController.builder()
			.postService(this.postService)
			.build();
	}

}
