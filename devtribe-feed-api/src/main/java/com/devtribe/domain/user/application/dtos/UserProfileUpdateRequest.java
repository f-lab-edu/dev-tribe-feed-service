package com.devtribe.domain.user.application.dtos;

import com.devtribe.domain.user.entity.CareerInterest;
import com.devtribe.domain.user.entity.CareerLevel;

public record UserProfileUpdateRequest(
    String companyName,
    String jobTitle,
    String githubUrl,
    String linkedinUrl,
    String blogUrl,
    CareerLevel careerLevel,
    CareerInterest careerInterest
) {

}
