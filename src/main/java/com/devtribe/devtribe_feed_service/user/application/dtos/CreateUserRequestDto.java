package com.devtribe.devtribe_feed_service.user.application.dtos;

import com.devtribe.devtribe_feed_service.user.domain.User;
import com.devtribe.devtribe_feed_service.user.domain.UserInfo;

public record CreateUserRequestDto(String email, String password, String nickname,
                                   String biography, String companyName, String jobTitle,
                                   String githubUrl, String linkedinUrl, String blogUrl) {

    public User toUser() {
        return User.builder()
            .email(email)
            .password(password)
            .nickname(nickname)
            .userInfo(
                UserInfo.builder()
                    .biography(biography)
                    .companyName(companyName)
                    .jobTitle(jobTitle)
                    .githubUrl(githubUrl)
                    .linkedinUrl(linkedinUrl)
                    .blogUrl(blogUrl)
                    .build()
            )
            .build();
    }
}
