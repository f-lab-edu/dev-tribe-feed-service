package com.devtribe.devtribe_feed_service.user.application.dtos;


import com.devtribe.devtribe_feed_service.user.domain.User;

public record CreateUserResponse(Long userId, String nickname) {

    public CreateUserResponse(User user) {
        this(
            user.getId(),
            user.getNickname()
        );
    }
}
