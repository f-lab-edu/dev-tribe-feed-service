package com.devtribe.devtribe_feed_service.user.domain;

import lombok.Builder;

public class User {

    private String id;
    private final String email;
    private final String nickname;
    private final String password;
    private final UserInfo userInfo;

    @Builder
    public User(String email, String nickname, String password, UserInfo userInfo) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.userInfo = userInfo;
    }
}
