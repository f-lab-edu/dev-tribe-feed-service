package com.devtribe.devtribe_feed_service.test.utils.fixtures

import com.devtribe.devtribe_feed_service.user.application.dtos.CreateUserRequest

class CreateUserRequestFixtures {

    static CreateUserRequest createRequiredUserRequest(String email, String password, String nickname, String biography) {
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

    static CreateUserRequest createUserRequestWithCredentials(String email, String password) {
        return new CreateUserRequest(
                email,
                password,
                "exampleUser",
                "Hello, I'm a new user!",
                "Example Corp",
                "Developer",
                "https://github.com/example",
                "https://linkedin.com/in/example",
                "https://example.com/blog"
        )
    }

    static CreateUserRequest createUserRequestWithNickname(String nickname) {
        return new CreateUserRequest(
                "user@example.com",
                "password123",
                nickname,
                "Hello, I'm a new user!",
                "Example Corp",
                "Developer",
                "https://github.com/example",
                "https://linkedin.com/in/example",
                "https://example.com/blog"
        )
    }

    static CreateUserRequest createUserRequestWithBiography(String biography) {
        return new CreateUserRequest(
                "user@example.com",
                "password123",
                "exampleUser",
                biography,
                "Example Corp",
                "Developer",
                "https://github.com/example",
                "https://linkedin.com/in/example",
                "https://example.com/blog"
        )
    }

}
