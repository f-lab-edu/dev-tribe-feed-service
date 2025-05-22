package com.devtribe.fixtures.user.dto

import com.devtribe.domain.user.application.dtos.CreateUserRequest

class CreateUserRequestFixtures {

    static CreateUserRequest createRequiredUserRequest(String email, String password, String nickname="exampleUser", String biography="Hello, I'm a new user!") {
        return new CreateUserRequest(
                email,
                password,
                nickname,
                biography,
                "Example Corp",
                "Developer",
                "https://github.com/example",
                "https://linkedin.com/in/example",
                "https://example.com/blog"
        )
    }

}
