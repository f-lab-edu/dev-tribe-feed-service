package com.devtribe.domain.user.application.dtos;

import com.devtribe.domain.user.entity.User;

public record CreateUserResponse(Long userId, String nickname) {

    public CreateUserResponse(User user) {
        this(
            user.getId(),
            user.getNickname()
        );
    }
}
