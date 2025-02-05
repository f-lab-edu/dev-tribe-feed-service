package com.devtribe.devtribe_feed_service.user.domain;

import lombok.Builder;

public class User {

    private String id;
    private final String email;
    private final String nickname;
    private final String password;
    private final String biography;
    private final String companyName;
    private final String jobTitle;
    private final String githubUrl;
    private final String linkedinUrl;
    private final String blogUrl;

    @Builder
    public User(String email, String nickname, String password, String biography,
        String companyName, String jobTitle, String githubUrl, String linkedinUrl, String blogUrl) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.biography = biography;
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.githubUrl = githubUrl;
        this.linkedinUrl = linkedinUrl;
        this.blogUrl = blogUrl;
    }
}
