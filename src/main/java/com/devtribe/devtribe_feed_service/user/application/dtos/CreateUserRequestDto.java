package com.devtribe.devtribe_feed_service.user.application.dtos;

public record CreateUserRequestDto(String email, String password, String nickname,
                                   String biography, String companyName, String jobTitle,
                                   String githubUrl, String linkedinUrl, String blogUrl) {

}
