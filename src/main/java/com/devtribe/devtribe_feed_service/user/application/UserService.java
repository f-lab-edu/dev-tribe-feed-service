package com.devtribe.devtribe_feed_service.user.application;

import com.devtribe.devtribe_feed_service.user.application.dtos.CreateUserRequestDto;
import com.devtribe.devtribe_feed_service.user.application.interfaces.UserRepository;
import com.devtribe.devtribe_feed_service.user.domain.User;
import com.devtribe.devtribe_feed_service.user.domain.UserInfo;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(CreateUserRequestDto dto) {
        User user = dto.toUser();
        return userRepository.save(user);
    }

}
