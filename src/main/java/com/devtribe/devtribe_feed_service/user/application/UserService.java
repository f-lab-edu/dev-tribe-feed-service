package com.devtribe.devtribe_feed_service.user.application;

import com.devtribe.devtribe_feed_service.user.application.dtos.CreateUserRequest;
import com.devtribe.devtribe_feed_service.user.application.interfaces.UserRepository;
import com.devtribe.devtribe_feed_service.user.domain.User;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(CreateUserRequest request) {
        User user = request.toUser();
        return userRepository.save(user);
    }

}
