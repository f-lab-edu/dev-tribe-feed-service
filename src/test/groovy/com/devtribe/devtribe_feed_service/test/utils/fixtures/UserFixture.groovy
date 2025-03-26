package com.devtribe.devtribe_feed_service.test.utils.fixtures

import com.devtribe.devtribe_feed_service.user.domain.User

class UserFixture {

    static User getUser(Long userId, String email = "email", String password = "password", String biography = "biography",
                        String companyName = "company", String jobTitle = "backend", String githubUrl = "github/devtribe",
                        String linkedinUrl = "linkedIn", String blogUrl = "blog.com") {
        return new User(
                id: userId,
                email: email,
                password: password,
                biography: biography,
                companyName: companyName,
                jobTitle: jobTitle,
                githubUrl: githubUrl,
                linkedinUrl: linkedinUrl,
                blogUrl: blogUrl
        )
    }
}