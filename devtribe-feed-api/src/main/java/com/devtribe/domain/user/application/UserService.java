package com.devtribe.domain.user.application;

import com.devtribe.domain.user.application.dtos.CreateUserRequest;
import com.devtribe.domain.user.application.dtos.CreateUserResponse;
import com.devtribe.domain.user.application.dtos.UserProfileResponse;
import com.devtribe.domain.user.application.dtos.UserProfileUpdateRequest;
import com.devtribe.domain.user.application.validators.CreateUserRequestValidator;
import com.devtribe.domain.user.application.validators.EmailValidator;
import com.devtribe.domain.user.application.validators.PasswordValidator;
import com.devtribe.domain.user.dao.UserRepository;
import com.devtribe.domain.user.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailValidator emailValidator;
    private final PasswordValidator passwordValidator;
    private final PasswordEncoder passwordEncoder;
    private final CreateUserRequestValidator createUserRequestValidator;

    public UserService(UserRepository userRepository, EmailValidator emailValidator,
        PasswordValidator passwordValidator, PasswordEncoder passwordEncoder,
        CreateUserRequestValidator createUserRequestValidator) {
        this.userRepository = userRepository;
        this.emailValidator = emailValidator;
        this.passwordValidator = passwordValidator;
        this.passwordEncoder = passwordEncoder;
        this.createUserRequestValidator = createUserRequestValidator;
    }

    @Transactional(readOnly = true)
    public User getUser(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
    }

    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) {
        validateCreateUserRequest(request);
        checkEmailAlreadyRegistered(request);
        checkNicknameAlreadyUsed(request);
        CreateUserRequest encodeRequest = encodePassword(request);

        User savedUser = userRepository.save(encodeRequest.toEntity());
        return new CreateUserResponse(savedUser);
    }

    @Transactional
    public void updateUserProfile(Long userId, UserProfileUpdateRequest request) {
        User user = getUser(userId);
        user.updateProfile(
            request.companyName(),
            request.jobTitle(),
            request.githubUrl(),
            request.linkedinUrl(),
            request.blogUrl(),
            request.careerLevel(),
            request.careerInterest()
        );
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getUserProfile(Long userId) {
        User user = getUser(userId);
        return UserProfileResponse.from(user);
    }

    private CreateUserRequest encodePassword(CreateUserRequest request) {
        String encoded = passwordEncoder.encode(request.password());
        return request.changePassword(encoded);
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
