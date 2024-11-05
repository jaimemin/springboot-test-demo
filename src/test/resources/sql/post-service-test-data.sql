insert into `users` (`id`, `email`, `nickname`, `address`, `certification_code`, `status`, `last_login_at`)
values (1, 'aaa@gmail.com', 'aaa', 'Seoul', 'aaaaaaaa-aaaaaaaa-aaaaaaa', 'ACTIVE', 0);

insert into `users` (`id`, `email`, `nickname`, `address`, `certification_code`, `status`, `last_login_at`)
values (2, 'aadda@gmail.com', 'aadda', 'Seoul', 'aaaaaaaa-aaaaaaaa-aaaaaba', 'PENDING', 0);

insert into `posts` (`id`, `content`, `created_at`, `modified_at`, `user_id`)
values (1, 'helloworld', 1678530673958, 1678530673958, 1);