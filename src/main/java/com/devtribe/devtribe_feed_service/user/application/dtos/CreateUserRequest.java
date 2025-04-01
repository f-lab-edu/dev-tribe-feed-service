package com.devtribe.devtribe_feed_service.user.application.dtos;

import com.devtribe.devtribe_feed_service.user.domain.User;

public record CreateUserRequest(String email, String password, String nickname,
                                String biography, String companyName, String jobTitle,
                                String githubUrl, String linkedinUrl, String blogUrl) {

    public CreateUserRequest changePassword(String password) {
        return new CreateUserRequest(
            email,
            password,
            nickname,
            biography,
            companyName,
            jobTitle,
            githubUrl,
            linkedinUrl,
            blogUrl
        );
    }

    public User toEntity() {
        return User.builder()
            .email(email)
            .password(password)
            .nickname(nickname)
            .biography(biography)
            .companyName(companyName)
            .jobTitle(jobTitle)
            .githubUrl(githubUrl)
            .linkedinUrl(linkedinUrl)
            .blogUrl(blogUrl)
            .build();
    }
}
