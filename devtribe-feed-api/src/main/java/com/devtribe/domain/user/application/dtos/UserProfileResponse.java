package com.devtribe.domain.user.application.dtos;

import com.devtribe.domain.user.entity.CareerInterest;
import com.devtribe.domain.user.entity.CareerLevel;
import com.devtribe.domain.user.entity.User;

public record UserProfileResponse(
    String email,
    String nickname,
    String biography,
    String companyName,
    String jobTitle,
    String githubUrl,
    String linkedinUrl,
    String blogUrl,
    CareerLevel careerLevel,
    CareerInterest careerInterest
) {

    public static UserProfileResponse from(User user) {
        return new UserProfileResponse(
            user.getEmail(),
            user.getNickname(),
            user.getBiography(),
            user.getCompanyName(),
            user.getJobTitle(),
            user.getGithubUrl(),
            user.getLinkedinUrl(),
            user.getBlogUrl(),
            user.getCareerLevel(),
            user.getCareerInterest()
        );
    }
}
