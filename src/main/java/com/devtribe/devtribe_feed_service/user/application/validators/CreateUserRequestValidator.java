package com.devtribe.devtribe_feed_service.user.application.validators;

public class CreateUserRequestValidator {

    public static final Integer MAX_BIOGRAPHY_LENGTH = 100;

    public void validateBiography(String biography) {
        if (biography == null) {
            return;
        }
        
        if (biography.length() > MAX_BIOGRAPHY_LENGTH) {
            throw new IllegalArgumentException("자기소개는 " + MAX_BIOGRAPHY_LENGTH + "자를 초과할 수 없습니다.");
        }
    }
}
