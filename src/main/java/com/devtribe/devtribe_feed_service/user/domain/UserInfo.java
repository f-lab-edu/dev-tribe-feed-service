package com.devtribe.devtribe_feed_service.user.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;

@Builder
@EqualsAndHashCode
public class UserInfo {

    public static final Integer MAX_BIOGRAPHY_LENGTH = 100;

    private String biography;
    private String companyName;
    private String jobTitle;
    private String githubUrl;
    private String linkedinUrl;
    private String blogUrl;

    public UserInfo(String biography, String companyName, String jobTitle, String githubUrl,
        String linkedinUrl, String blogUrl) {
        validateBiography(biography);

        this.biography = biography;
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.githubUrl = githubUrl;
        this.linkedinUrl = linkedinUrl;
        this.blogUrl = blogUrl;
    }

    private void validateBiography(String biography) {

        if (biography != null && biography.length() > MAX_BIOGRAPHY_LENGTH) {
            throw new IllegalArgumentException("자기소개는 " + MAX_BIOGRAPHY_LENGTH + "자를 초과할 수 없습니다.");
        }
    }
}
