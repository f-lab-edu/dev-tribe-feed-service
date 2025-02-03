package com.devtribe.devtribe_feed_service.user.domain;

import lombok.Builder;

public class User {

    private String id;
    private final Email email;
    private final String nickname;
    private final Password password;
    private final UserInfo userInfo;

    @Builder
    public User(Email email, String nickname, Password password, UserInfo userInfo) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.userInfo = userInfo;
    }
}
