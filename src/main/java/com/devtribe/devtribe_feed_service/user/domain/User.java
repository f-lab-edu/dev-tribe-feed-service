package com.devtribe.devtribe_feed_service.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    private String id;
    private String email;
    private String nickname;
    private String password;
    private String biography;
    private String companyName;
    private String jobTitle;
    private String githubUrl;
    private String linkedinUrl;
    private String blogUrl;

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
