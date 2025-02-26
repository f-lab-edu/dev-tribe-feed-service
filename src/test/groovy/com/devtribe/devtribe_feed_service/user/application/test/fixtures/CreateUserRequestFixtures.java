package com.devtribe.devtribe_feed_service.user.application.test.fixtures;

import com.devtribe.devtribe_feed_service.user.application.dtos.CreateUserRequest;

public class CreateUserRequestFixtures {

    private static final String defaultEmail = "email@gmail.com";
    private static final String defaultPassword = "password!@34";
    private static final String defaultNickname = "nickname";
    private static final String defaultBiography = "biography";

    private static final String defaultCompanyName = "devtribeCompany";
    private static final String defaultJobTitle = "programmer";
    private static final String defaultGithubUrl = "devtribeGithub";
    private static final String defaultLinkedinUrl = "devtribeLinkedin";
    private static final String defaultBlogGithubUrl = "devtribeBlog";

    public static CreateUserRequest validRequest() {
        return new CreateUserRequest(defaultEmail, defaultPassword, defaultNickname,
            defaultBiography, defaultCompanyName, defaultJobTitle, defaultGithubUrl,
            defaultLinkedinUrl, defaultBlogGithubUrl);
    }

    public static CreateUserRequest createUserRequest(String email, String password,
        String nickname, String biography) {
        return new CreateUserRequest(email, password, nickname, biography, defaultCompanyName,
            defaultJobTitle, defaultGithubUrl, defaultLinkedinUrl, defaultBlogGithubUrl);
    }

}
