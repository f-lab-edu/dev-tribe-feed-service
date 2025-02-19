package com.devtribe.devtribe_feed_service.user.application;

import com.devtribe.devtribe_feed_service.user.application.dtos.CreateUserRequest;
import com.devtribe.devtribe_feed_service.user.application.interfaces.UserRepository;
import com.devtribe.devtribe_feed_service.user.application.validators.CreateUserRequestValidator;
import com.devtribe.devtribe_feed_service.user.application.validators.EmailValidator;
import com.devtribe.devtribe_feed_service.user.application.validators.PasswordValidator;
import com.devtribe.devtribe_feed_service.user.domain.User;

public class UserService {

    private final UserRepository userRepository;
    private final EmailValidator emailValidator;
    private final PasswordValidator passwordValidator;
    private final CreateUserRequestValidator createUserRequestValidator;

    public UserService(UserRepository userRepository, EmailValidator emailValidator,
        PasswordValidator passwordValidator,
        CreateUserRequestValidator createUserRequestValidator) {
        this.userRepository = userRepository;
        this.emailValidator = emailValidator;
        this.passwordValidator = passwordValidator;
        this.createUserRequestValidator = createUserRequestValidator;
    }

    public User createUser(CreateUserRequest request) {
        validateCreateUserRequest(request);

        User user = request.toEntity();
        return userRepository.save(user);
    }

    private void validateCreateUserRequest(CreateUserRequest request) {
        createUserRequestValidator.validateBiography(request.biography());
        emailValidator.validateEmail(request.email());
        passwordValidator.validatePassword(request.password());
    }
}
