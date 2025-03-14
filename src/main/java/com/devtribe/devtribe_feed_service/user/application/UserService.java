package com.devtribe.devtribe_feed_service.user.application;

import com.devtribe.devtribe_feed_service.user.application.dtos.CreateUserRequest;
import com.devtribe.devtribe_feed_service.user.application.interfaces.UserRepository;
import com.devtribe.devtribe_feed_service.user.application.validators.CreateUserRequestValidator;
import com.devtribe.devtribe_feed_service.user.application.validators.EmailValidator;
import com.devtribe.devtribe_feed_service.user.application.validators.PasswordValidator;
import com.devtribe.devtribe_feed_service.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
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

    public User getUser(Long userId) {
        // TODO: userId를 갖는 User를 반환.
        return null;
    }

    @Transactional
    public User createUser(CreateUserRequest request) {
        validateCreateUserRequest(request);
        checkEmailAlreadyRegistered(request);
        checkNicknameAlreadyUsed(request);

        User user = request.toEntity();
        return userRepository.save(user);
    }

    private void checkEmailAlreadyRegistered(CreateUserRequest request) {
        if (userRepository.isEmailRegistered(request.email())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
    }

    private void checkNicknameAlreadyUsed(CreateUserRequest request) {
        if (userRepository.isNicknameUsed(request.nickname())) {
            throw new IllegalArgumentException("이미 사용중인 닉네임입니다.");
        }
    }

    private void validateCreateUserRequest(CreateUserRequest request) {
        createUserRequestValidator.validateBiography(request.biography());
        emailValidator.validateEmail(request.email());
        passwordValidator.validatePassword(request.password());
    }
}
